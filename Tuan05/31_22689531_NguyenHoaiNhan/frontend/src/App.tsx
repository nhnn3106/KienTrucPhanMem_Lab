import { type FormEvent, useEffect, useMemo, useState } from "react";
import "./App.css";

type MenuItem = {
  id: number;
  name: string;
  price: number;
  restaurantId?: number;
};

type Restaurant = {
  id: number;
  name: string;
  address: string;
  menuItems: MenuItem[];
};

type OrderItemInput = {
  menuItemId: number;
  quantity: number;
};

type OrderItem = {
  id: number;
  menuItemId: number;
  menuItemName: string;
  unitPrice: number;
  quantity: number;
  lineTotal: number;
};

type OrderResponse = {
  id: number;
  customerName: string;
  createdAt: string;
  totalAmount: number;
  items: OrderItem[];
};

const RESTAURANTS_API =
  import.meta.env.VITE_RESTAURANTS_API ?? "http://localhost:8081/api";
const ORDERS_API =
  import.meta.env.VITE_ORDERS_API ?? "http://localhost:8082/api";

const formatMoney = (value: number) =>
  new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    maximumFractionDigits: 0,
  }).format(value);

function App() {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [loadError, setLoadError] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [orderItems, setOrderItems] = useState<OrderItemInput[]>([
    { menuItemId: 0, quantity: 1 },
  ]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState("");
  const [orderResult, setOrderResult] = useState<OrderResponse | null>(null);

  const menuItems = useMemo(
    () =>
      restaurants.flatMap((restaurant) =>
        restaurant.menuItems.map((item) => ({
          ...item,
          restaurantName: restaurant.name,
        })),
      ),
    [restaurants],
  );

  const loadRestaurants = async () => {
    setIsLoading(true);
    setLoadError("");
    try {
      const response = await fetch(`${RESTAURANTS_API}/restaurants`);
      if (!response.ok) {
        throw new Error("Failed to load restaurants");
      }
      const data = (await response.json()) as Restaurant[];
      setRestaurants(data);
    } catch (error) {
      setLoadError((error as Error).message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadRestaurants();
  }, []);

  const addItemRow = () => {
    setOrderItems((items) => [...items, { menuItemId: 0, quantity: 1 }]);
  };

  const updateItem = (
    index: number,
    field: keyof OrderItemInput,
    value: number,
  ) => {
    setOrderItems((items) =>
      items.map((item, itemIndex) =>
        itemIndex === index ? { ...item, [field]: value } : item,
      ),
    );
  };

  const removeItem = (index: number) => {
    setOrderItems((items) =>
      items.filter((_, itemIndex) => itemIndex !== index),
    );
  };

  const submitOrder = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSubmitError("");
    setOrderResult(null);

    const payloadItems = orderItems.filter((item) => item.menuItemId > 0);
    if (!customerName.trim()) {
      setSubmitError("Please enter a customer name.");
      return;
    }
    if (payloadItems.length === 0) {
      setSubmitError("Choose at least one menu item.");
      return;
    }

    setIsSubmitting(true);
    try {
      const response = await fetch(`${ORDERS_API}/orders`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          customerName: customerName.trim(),
          items: payloadItems,
        }),
      });
      if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Order failed");
      }
      const data = (await response.json()) as OrderResponse;
      setOrderResult(data);
    } catch (error) {
      setSubmitError((error as Error).message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="page">
      <header className="hero">
        <div>
          <p className="eyebrow">Microservices demo</p>
          <h1>Food order flow</h1>
          <p className="lead">
            Restaurant Service provides menu data, Order Service stores orders
            and calls the restaurant service for validation.
          </p>
          <div className="hero-actions">
            <button
              className="ghost"
              onClick={loadRestaurants}
              disabled={isLoading}>
              {isLoading ? "Loading..." : "Reload data"}
            </button>
            <span className="status">
              {loadError ?
                `Error: ${loadError}`
              : `Restaurants: ${restaurants.length} | Menu items: ${menuItems.length}`
              }
            </span>
          </div>
        </div>
        <div className="hero-panel">
          <div>
            <p className="panel-title">APIs</p>
            <p className="panel-text">Restaurant: {RESTAURANTS_API}</p>
            <p className="panel-text">Order: {ORDERS_API}</p>
          </div>
          <div className="panel-highlight">
            <p className="panel-title">Order hint</p>
            <p className="panel-text">Pick a menu item ID from the list.</p>
          </div>
        </div>
      </header>

      <section className="grid">
        <div className="card restaurants-card">
          <div className="card-header">
            <div>
              <h2>Restaurants</h2>
              <p>Data from MySQL via Restaurant Service.</p>
            </div>
          </div>
          {restaurants.length === 0 && !isLoading ?
            <p className="empty">
              No restaurants yet. Add data or restart the service.
            </p>
          : <div className="restaurant-list">
              {restaurants.map((restaurant) => (
                <article className="restaurant" key={restaurant.id}>
                  <header>
                    <h3>{restaurant.name}</h3>
                    <p>{restaurant.address}</p>
                  </header>
                  <ul className="menu-list">
                    {restaurant.menuItems.map((item) => (
                      <li key={item.id}>
                        <div>
                          <span className="menu-name">{item.name}</span>
                          <span className="menu-price">
                            {formatMoney(item.price)}
                          </span>
                        </div>
                        <span className="menu-id">ID {item.id}</span>
                      </li>
                    ))}
                  </ul>
                </article>
              ))}
            </div>
          }
        </div>

        <div className="card order-card">
          <div className="card-header">
            <div>
              <h2>Create order</h2>
              <p>Stored in PostgreSQL, validated by the Restaurant Service.</p>
            </div>
          </div>
          <form className="order-form" onSubmit={submitOrder}>
            <label>
              Customer name
              <input
                type="text"
                value={customerName}
                onChange={(event) => setCustomerName(event.target.value)}
                placeholder="Nguyen Hoai Nhan"
                required
              />
            </label>

            <div className="order-items">
              <div className="items-header">
                <span>Menu items</span>
                <button type="button" className="ghost" onClick={addItemRow}>
                  Add item
                </button>
              </div>
              {orderItems.map((item, index) => (
                <div className="item-row" key={`item-${index}`}>
                  <select
                    value={item.menuItemId}
                    onChange={(event) =>
                      updateItem(
                        index,
                        "menuItemId",
                        Number(event.target.value),
                      )
                    }>
                    <option value={0}>Choose menu item</option>
                    {menuItems.map((menuItem) => (
                      <option key={menuItem.id} value={menuItem.id}>
                        {menuItem.name} - {formatMoney(menuItem.price)} (
                        {menuItem.restaurantName})
                      </option>
                    ))}
                  </select>
                  <input
                    type="number"
                    min={1}
                    value={item.quantity}
                    onChange={(event) =>
                      updateItem(index, "quantity", Number(event.target.value))
                    }
                  />
                  <button
                    type="button"
                    className="ghost"
                    onClick={() => removeItem(index)}
                    disabled={orderItems.length === 1}>
                    Remove
                  </button>
                </div>
              ))}
            </div>

            <button className="primary" type="submit" disabled={isSubmitting}>
              {isSubmitting ? "Sending..." : "Place order"}
            </button>
            {submitError ?
              <p className="error">{submitError}</p>
            : null}
          </form>

          {orderResult ?
            <div className="order-result">
              <h3>Order #{orderResult.id}</h3>
              <p>
                {orderResult.customerName} | Total{" "}
                {formatMoney(orderResult.totalAmount)}
              </p>
              <ul>
                {orderResult.items.map((item) => (
                  <li key={item.id}>
                    {item.menuItemName} x{item.quantity} ={" "}
                    {formatMoney(item.lineTotal)}
                  </li>
                ))}
              </ul>
            </div>
          : null}
        </div>
      </section>
    </div>
  );
}

export default App;
