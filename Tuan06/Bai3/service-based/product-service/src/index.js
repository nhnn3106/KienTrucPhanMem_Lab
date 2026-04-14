import cors from "cors";
import dotenv from "dotenv";
import express from "express";
import mongoose from "mongoose";

dotenv.config();

const app = express();
const port = process.env.PORT || 3002;
const mongoUri =
  process.env.MONGODB_URI || "mongodb://localhost:27017/svc_products";

mongoose
  .connect(mongoUri)
  .then(() => console.log("Connected to MongoDB"))
  .catch((error) => {
    console.error("MongoDB connection error", error);
    process.exit(1);
  });

const productSchema = new mongoose.Schema(
  {
    name: { type: String, required: true },
    price: { type: Number, required: true },
    description: { type: String, default: "" },
  },
  { timestamps: true },
);

const Product = mongoose.model("Product", productSchema);

app.use(cors());
app.use(express.json());

app.get("/products", async (req, res) => {
  const products = await Product.find().sort({ createdAt: -1 });
  res.json(products);
});

app.get("/products/:id", async (req, res) => {
  const product = await Product.findById(req.params.id);
  if (!product) {
    return res.status(404).send("Product not found");
  }
  res.json(product);
});

app.post("/products", async (req, res) => {
  try {
    const product = await Product.create(req.body);
    res.status(201).json(product);
  } catch (error) {
    res.status(400).send(error.message);
  }
});

app.put("/products/:id", async (req, res) => {
  try {
    const product = await Product.findByIdAndUpdate(req.params.id, req.body, {
      new: true,
      runValidators: true,
    });
    if (!product) {
      return res.status(404).send("Product not found");
    }
    res.json(product);
  } catch (error) {
    res.status(400).send(error.message);
  }
});

app.delete("/products/:id", async (req, res) => {
  const product = await Product.findByIdAndDelete(req.params.id);
  if (!product) {
    return res.status(404).send("Product not found");
  }
  res.status(204).send();
});

app.listen(port, () => {
  console.log(`Product service listening on ${port}`);
});
