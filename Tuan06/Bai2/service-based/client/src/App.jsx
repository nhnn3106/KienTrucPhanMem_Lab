import { useEffect, useMemo, useState } from "react";
import {
  createProduct,
  deleteProduct,
  fetchProducts,
  updateProduct,
} from "./api";

const emptyForm = {
  name: "",
  price: "",
  description: "",
  stock: "",
};

function normalizePayload(form) {
  return {
    name: form.name.trim(),
    price: Number(form.price),
    description: form.description.trim(),
    stock: Number(form.stock),
  };
}

export default function App() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const filtered = useMemo(() => {
    const lower = query.trim().toLowerCase();
    if (!lower) {
      return products;
    }
    return products.filter((product) =>
      product.name.toLowerCase().includes(lower),
    );
  }, [products, query]);

  async function loadProducts() {
    setLoading(true);
    setError("");
    try {
      const data = await fetchProducts();
      setProducts(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadProducts();
  }, []);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  function handleEdit(product) {
    setEditingId(product._id);
    setForm({
      name: product.name,
      price: product.price,
      description: product.description,
      stock: product.stock,
    });
  }

  function resetForm() {
    setEditingId(null);
    setForm(emptyForm);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    const payload = normalizePayload(form);

    if (!payload.name) {
      setError("Name is required");
      return;
    }

    if (Number.isNaN(payload.price) || Number.isNaN(payload.stock)) {
      setError("Price and stock must be numbers");
      return;
    }

    try {
      if (editingId) {
        const updated = await updateProduct(editingId, payload);
        setProducts((prev) =>
          prev.map((item) => (item._id === updated._id ? updated : item)),
        );
      } else {
        const created = await createProduct(payload);
        setProducts((prev) => [created, ...prev]);
      }
      resetForm();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    setError("");
    try {
      await deleteProduct(id);
      setProducts((prev) => prev.filter((item) => item._id !== id));
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div className="page">
      <header>
        <h1>Service-Based Product Manager</h1>
        <p>Gateway + Product + Inventory services.</p>
      </header>

      <section className="panel">
        <h2>{editingId ? "Edit product" : "Create product"}</h2>
        <form onSubmit={handleSubmit} className="form">
          <label>
            Name
            <input name="name" value={form.name} onChange={handleChange} />
          </label>
          <label>
            Price
            <input
              name="price"
              type="number"
              step="0.01"
              value={form.price}
              onChange={handleChange}
            />
          </label>
          <label>
            Description
            <textarea
              name="description"
              value={form.description}
              onChange={handleChange}
            />
          </label>
          <label>
            Stock
            <input
              name="stock"
              type="number"
              value={form.stock}
              onChange={handleChange}
            />
          </label>
          <div className="actions">
            <button type="submit">{editingId ? "Update" : "Create"}</button>
            {editingId ?
              <button type="button" onClick={resetForm} className="ghost">
                Cancel
              </button>
            : null}
          </div>
        </form>
        {error ?
          <p className="error">{error}</p>
        : null}
      </section>

      <section className="panel">
        <div className="toolbar">
          <h2>Products</h2>
          <input
            placeholder="Search by name"
            value={query}
            onChange={(event) => setQuery(event.target.value)}
          />
        </div>

        {loading ?
          <p>Loading...</p>
        : null}
        {!loading && filtered.length === 0 ?
          <p>No products found.</p>
        : <div className="grid">
            {filtered.map((product) => (
              <article key={product._id} className="card">
                <h3>{product.name}</h3>
                <p className="price">${product.price.toFixed(2)}</p>
                <p className="description">{product.description}</p>
                <p className="stock">Stock: {product.stock}</p>
                <div className="card-actions">
                  <button onClick={() => handleEdit(product)}>Edit</button>
                  <button
                    className="danger"
                    onClick={() => handleDelete(product._id)}>
                    Delete
                  </button>
                </div>
              </article>
            ))}
          </div>
        }
      </section>
    </div>
  );
}
