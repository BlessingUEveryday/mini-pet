$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

$jarName = "mini-pet-1.0.0-SNAPSHOT.jar"
$inputDirectory = Join-Path $projectRoot "package-input"
$outputDirectory = Join-Path $projectRoot "package-output"

mvn clean package

if (Test-Path $inputDirectory) {
    Remove-Item $inputDirectory -Recurse -Force
}

if (Test-Path $outputDirectory) {
    Remove-Item $outputDirectory -Recurse -Force
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
    --app-version 1.0.0 `
    --vendor BlessingUEveryday `
    --description "A small desktop pet built with Java Swing." `
    --dest $outputDirectory

Write-Host "Built application: $outputDirectory\MiniPet\MiniPet.exe"