import axios from "axios";
import cors from "cors";
import dotenv from "dotenv";
import express from "express";

dotenv.config();

const app = express();
const port = process.env.PORT || 3001;
const productServiceUrl =
  process.env.PRODUCT_SERVICE_URL || "http://localhost:3002";
const inventoryServiceUrl =
  process.env.INVENTORY_SERVICE_URL || "http://localhost:3003";

const productApi = axios.create({ baseURL: productServiceUrl });
const inventoryApi = axios.create({ baseURL: inventoryServiceUrl });

app.use(cors());
app.use(express.json());

async function fetchInventory(productId) {
  try {
    const response = await inventoryApi.get(`/inventory/${productId}`);
    return response.data.stock ?? 0;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      return 0;
    }
    throw error;
  }
}

app.get("/api/products", async (req, res) => {
  try {
    const productsResponse = await productApi.get("/products");
    const products = await Promise.all(
      productsResponse.data.map(async (product) => ({
        ...product,
        stock: await fetchInventory(product._id),
      })),
    );
    res.json(products);
  } catch (error) {
    res.status(500).send(error.message);
  }
});

app.get("/api/products/:id", async (req, res) => {
  try {
    const productResponse = await productApi.get(`/products/${req.params.id}`);
    const stock = await fetchInventory(req.params.id);
    res.json({ ...productResponse.data, stock });
  } catch (error) {
    res.status(error.response?.status || 500).send(error.message);
  }
});

app.post("/api/products", async (req, res) => {
  try {
    const { stock, ...productPayload } = req.body;
    const productResponse = await productApi.post("/products", productPayload);
    const product = productResponse.data;
    await inventoryApi.put(`/inventory/${product._id}`, {
      stock: Number(stock) || 0,
    });
    res.status(201).json({ ...product, stock: Number(stock) || 0 });
  } catch (error) {
    res.status(error.response?.status || 500).send(error.message);
  }
});

app.put("/api/products/:id", async (req, res) => {
  try {
    const { stock, ...productPayload } = req.body;
    const productResponse = await productApi.put(
      `/products/${req.params.id}`,
      productPayload,
    );

    if (stock !== undefined) {
      await inventoryApi.put(`/inventory/${req.params.id}`, {
        stock: Number(stock) || 0,
      });
    }

    const latestStock = await fetchInventory(req.params.id);
    res.json({ ...productResponse.data, stock: latestStock });
  } catch (error) {
    res.status(error.response?.status || 500).send(error.message);
  }
});

app.delete("/api/products/:id", async (req, res) => {
  try {
    await productApi.delete(`/products/${req.params.id}`);
    await inventoryApi.delete(`/inventory/${req.params.id}`);
    res.status(204).send();
  } catch (error) {
    res.status(error.response?.status || 500).send(error.message);
  }
});

app.listen(port, () => {
  console.log(`Gateway listening on ${port}`);
});
