#!/bin/sh
echo "[Sidecar] Iniciando sidecar de logging"
while sleep 5; do
  echo "[Sidecar] Checando serviço em http://localhost:8080/ping"
  curl -sf http://localhost:8080/ping && echo "[Sidecar] ping OK" || echo "[Sidecar] ping FAILED"
done
