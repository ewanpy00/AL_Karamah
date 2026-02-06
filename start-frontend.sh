#!/bin/bash

echo "🎨 Запуск Frontend..."
cd "$(dirname "$0")/frontend"

# Установка зависимостей, если нужно
if [ ! -d "node_modules" ]; then
    echo "📦 Установка зависимостей..."
    npm install
fi

npm run dev

