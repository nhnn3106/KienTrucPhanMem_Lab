import cors from "cors";
import dotenv from "dotenv";
import express from "express";
import mongoose from "mongoose";

dotenv.config();

const app = express();
const port = process.env.PORT || 3003;
const mongoUri =
  process.env.MONGODB_URI || "mongodb://localhost:27017/svc_inventory";

mongoose
  .connect(mongoUri)
  .then(() => console.log("Connected to MongoDB"))
  .catch((error) => {
    console.error("MongoDB connection error", error);
    process.exit(1);
  });

const inventorySchema = new mongoose.Schema(
  {
    productId: { type: String, required: true, unique: true },
    stock: { type: Number, default: 0 },
  },
  { timestamps: true },
);

const Inventory = mongoose.model("Inventory", inventorySchema);

app.use(cors());
app.use(express.json());

app.get("/inventory/:productId", async (req, res) => {
  const entry = await Inventory.findOne({ productId: req.params.productId });
  if (!entry) {
    return res.status(404).send("Inventory not found");
  }
  res.json(entry);
});

app.put("/inventory/:productId", async (req, res) => {
  try {
    const entry = await Inventory.findOneAndUpdate(
      { productId: req.params.productId },
      { stock: Number(req.body.stock) || 0 },
      { new: true, upsert: true },
    );
    res.json(entry);
  } catch (error) {
    res.status(400).send(error.message);
  }
});

app.delete("/inventory/:productId", async (req, res) => {
  await Inventory.findOneAndDelete({ productId: req.params.productId });
  res.status(204).send();
});

app.listen(port, () => {
  console.log(`Inventory service listening on ${port}`);
});
