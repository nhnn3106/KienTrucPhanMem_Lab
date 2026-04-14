const API_BASE = "/api/products";

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Request failed");
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export function fetchProducts() {
  return request("");
}

export function createProduct(payload) {
  return request("", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function updateProduct(id, payload) {
  return request(`/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export function deleteProduct(id) {
  return request(`/${id}`, { method: "DELETE" });
}
