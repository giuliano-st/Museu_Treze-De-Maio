import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

export default defineConfig({
  plugins: [react()],
  // O build vai direto para a pasta static do Spring Boot
  build: {
    outDir: '../src/main/resources/static',
    emptyOutDir: true,
  },
})
