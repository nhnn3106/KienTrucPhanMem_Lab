import express from 'express'
import cors from 'cors'
import path from 'node:path'
import { existsSync } from 'node:fs'
import { fileURLToPath } from 'node:url'
import userRoutes from './routes/userRoutes.js'

const app = express()

app.use(cors())
app.use(express.json())

app.use('/api/users', userRoutes)

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
const clientDir = path.resolve(__dirname, '../client')
const indexPath = path.join(clientDir, 'index.html')

if (existsSync(indexPath)) {
    app.use(express.static(clientDir))
    app.get('*', (_req, res) => {
        res.sendFile(indexPath)
    })
}

const port = Number(process.env.PORT ?? 3000)
app.listen(port, () => {
    console.log(`Server running on port ${port}`)
})
