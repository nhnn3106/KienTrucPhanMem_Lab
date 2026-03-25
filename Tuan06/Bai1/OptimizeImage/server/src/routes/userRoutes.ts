import { Router } from 'express'
import { getUsers, postUser, removeUser } from '../controllers/userController.js'

const router = Router()

router.get('/', getUsers)
router.post('/', postUser)
router.delete('/:id', removeUser)

export default router
