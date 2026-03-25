import { Pool } from 'pg'

const pool = new Pool({
    host: process.env.PGHOST ?? 'localhost',
    port: Number(process.env.PGPORT ?? 5432),
    database: process.env.PGDATABASE ?? 'appdb',
    user: process.env.PGUSER ?? 'appuser',
    password: process.env.PGPASSWORD ?? 'apppass',
})

pool.on('error', (err) => {
    console.error('Unexpected database error:', err)
})

export default pool
