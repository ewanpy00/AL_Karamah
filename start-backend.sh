#!/bin/bash

echo "🚀 Запуск Backend..."
cd "$(dirname "$0")/backend"
mvn spring-boot:run

