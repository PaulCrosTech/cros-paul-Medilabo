#!/bin/sh
echo "MS_GATEWAY_USER=${MS_GATEWAY_USER}" > .env
echo "MS_GATEWAY_PASSWORD=${MS_GATEWAY_PASSWORD}" >> .env
exec "$@"