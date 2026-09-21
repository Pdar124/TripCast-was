# .env 파일의 값을 현재 PowerShell 세션의 환경변수로 불러온다.
# 사용법: 저장소 루트에서 ". .\load-env.ps1" (점 붙여서 source 해야 현재 세션에 적용됨)

$envFile = Join-Path $PSScriptRoot ".env"

if (-not (Test-Path $envFile)) {
    Write-Host "$envFile 이 없습니다. .env.example을 복사해서 .env를 만들고 값을 채워주세요."
    Write-Host "  Copy-Item .env.example .env"
    return
}

Get-Content $envFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -eq "" -or $line.StartsWith("#")) {
        return
    }

    $parts = $line.Split("=", 2)
    if ($parts.Length -ne 2) {
        return
    }

    $name = $parts[0].Trim()
    $value = $parts[1].Trim()
    Set-Item -Path "Env:$name" -Value $value
}

Write-Host ".env에서 환경변수를 불러왔습니다."
