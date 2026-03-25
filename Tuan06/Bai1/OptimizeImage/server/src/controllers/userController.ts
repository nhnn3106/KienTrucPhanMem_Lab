import type { Request, Response } from 'express'
import { createUser, deleteUser, listUsers, type NewUser } from '../models/userModel.js'

export const getUsers = async (_req: Request, res: Response) => {
    const users = await listUsers()
    res.json(users)
}

export const postUser = async (req: Request, res: Response) => {
    const { name, email, role, status } = req.body as Partial<NewUser>

    if (!name || !email) {
        res.status(400).json({ message: 'Name va email la bat buoc.' })
        return
    }

    try {
        const user = await createUser({
            name,
            email,
            role: role ?? 'Member',
            status: status ?? 'active',
        })
        res.status(201).json(user)
    } catch (err) {
        if (typeof err === 'object' && err && 'code' in err && err.code === '23505') {
            res.status(409).json({ message: 'Email da ton tai.' })
            return
        }
        res.status(500).json({ message: 'Khong the tao user.' })
    }
}

export const removeUser = async (req: Request, res: Response) => {
    const id = Number(req.params.id)
    if (Number.isNaN(id)) {
        res.status(400).json({ message: 'ID khong hop le.' })
        return
    }

    const deleted = await deleteUser(id)
    if (!deleted) {
        res.status(404).json({ message: 'Khong tim thay user.' })
        return
    }

    res.json(deleted)
}
