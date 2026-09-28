$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

$appVersion = "1.0.1"
$jarName = "mini-pet-1.0.0-SNAPSHOT.jar"
$inputDirectory = Join-Path $projectRoot "package-input"
$appImageOutputDirectory = Join-Path $projectRoot "package-output"
$installerOutputDirectory = Join-Path $projectRoot "installer-output\$appVersion"
$upgradeUuid = "27efce61-0cc7-4267-a390-67130f0a4975"

function Assert-LastExitCode {
    param([string]$operation)

    if ($LASTEXITCODE -ne 0) {
        throw "$operation failed with exit code $LASTEXITCODE."
    }
}

mvn clean package
Assert-LastExitCode "Maven package"

foreach ($directory in @(
    $inputDirectory,
    $appImageOutputDirectory,
    $installerOutputDirectory
)) {
    if (Test-Path $directory) {
        Remove-Item $directory -Recurse -Force
    }
}

New-Item -ItemType Directory $inputDirectory | Out-Null

Copy-Item `
    (Join-Path $projectRoot "target\$jarName") `
    $inputDirectory

jpackage `
    --type app-image `
    --input $inputDirectory `
    --name MiniPet `
    --main-jar $jarname `
    --main-class minipet.Main `
    --app-version $appVersion `
    --vendor BlessingUEveryday `
    --description "A small desktop pet built with Java Swing." `
    --dest $appImageOutputDirectory
Assert-LastExitCode "App image packaging"

jpackage `
    --type exe `
    --input $inputDirectory `
    --name MiniPet `
    --main-jar $jarName `
    --main-class minipet.Main `
    --app-version $appVersion `
    --vendor BlessingUEveryday `
    --description "A small desktop pet built with Java Swing." `
    --win-per-user-install `
    --win-dir-chooser `
    --win-shortcut `
    --win-menu `
    --win-menu-group "Mini Pet" `
    --win-upgrade-uuid $upgradeUuid `
    --dest $installerOutputDirectory
Assert-LastExitCode "Windows installer packaging"

Write-Host "Built app image: $appImageOutputDirectory\MiniPet\MiniPet.exe"
Write-Host "Built installer in: $installerOutputDirectory"
