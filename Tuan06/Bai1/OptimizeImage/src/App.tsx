import {
  useEffect,
  useMemo,
  useState,
  type ChangeEvent,
  type FormEvent,
} from "react";
import "./App.css";

type User = {
  id: number;
  name: string;
  email: string;
  role: string;
  status: "active" | "inactive";
  createdAt: string;
};

type NewUser = {
  name: string;
  email: string;
  role: string;
  status: "active" | "inactive";
};

const defaultForm: NewUser = {
  name: "",
  email: "",
  role: "Member",
  status: "active",
};

function App() {
  const [users, setUsers] = useState<User[]>([]);
  const [form, setForm] = useState<NewUser>(defaultForm);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const stats = useMemo(() => {
    const total = users.length;
    const active = users.filter((user) => user.status === "active").length;
    return { total, active };
  }, [users]);

  const loadUsers = async () => {
    setLoading(true);
    setError("");
    try {
      const response = await fetch("/api/users");
      if (!response.ok) {
        throw new Error("Khong the tai danh sach user.");
      }
      const data = (await response.json()) as User[];
      setUsers(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Co loi xay ra.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadUsers();
  }, []);

  const handleChange = (
    event: ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    setError("");
    try {
      const response = await fetch("/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (!response.ok) {
        const payload = (await response.json()) as { message?: string };
        throw new Error(payload.message ?? "Khong the tao user.");
      }
      const created = (await response.json()) as User;
      setUsers((prev) => [created, ...prev]);
      setForm(defaultForm);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Co loi xay ra.");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (userId: number) => {
    setError("");
    try {
      const response = await fetch(`/api/users/${userId}`, {
        method: "DELETE",
      });
      if (!response.ok) {
        throw new Error("Khong the xoa user.");
      }
      setUsers((prev) => prev.filter((user) => user.id !== userId));
    } catch (err) {
      setError(err instanceof Error ? err.message : "Co loi xay ra.");
    }
  };

  return (
    <div className="app">
      <header className="hero">
        <div>
          <span className="chip">MVC User Console</span>
          <h1>Quan ly user thong minh, gon gang, ro rang.</h1>
          <p>
            Ket noi MVC backend + giao dien nhanh nhe. Theo doi user, role, va
            trang thai trong mot bang dieu khien duy nhat.
          </p>
        </div>
        <div className="hero-card">
          <div>
            <span className="label">Tong user</span>
            <strong>{stats.total}</strong>
          </div>
          <div>
            <span className="label">Dang hoat dong</span>
            <strong>{stats.active}</strong>
          </div>
          <div>
            <span className="label">He thong</span>
            <strong>Online</strong>
          </div>
        </div>
      </header>

      <main className="content">
        <section className="panel">
          <div className="panel-title">
            <h2>Tao user moi</h2>
            <p>Nhap thong tin co ban de them vao danh sach.</p>
          </div>
          <form className="user-form" onSubmit={handleSubmit}>
            <label>
              Ho va ten
              <input
                name="name"
                value={form.name}
                onChange={handleChange}
                placeholder="Nguyen Van A"
                required
              />
            </label>
            <label>
              Email
              <input
                name="email"
                type="email"
                value={form.email}
                onChange={handleChange}
                placeholder="user@domain.com"
                required
              />
            </label>
            <label>
              Role
              <select name="role" value={form.role} onChange={handleChange}>
                <option>Member</option>
                <option>Admin</option>
                <option>Manager</option>
              </select>
            </label>
            <label>
              Trang thai
              <select name="status" value={form.status} onChange={handleChange}>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
              </select>
            </label>
            <button type="submit" disabled={saving}>
              {saving ? "Dang luu..." : "Them user"}
            </button>
          </form>
          {error ?
            <p className="error">{error}</p>
          : null}
        </section>

        <section className="panel">
          <div className="panel-title">
            <h2>Danh sach user</h2>
            <p>Cap nhat theo thoi gian thuc tu MVC backend.</p>
          </div>
          {loading ?
            <p className="muted">Dang tai du lieu...</p>
          : <div className="user-table">
              <div className="table-head">User</div>
              <div className="table-head">Role</div>
              <div className="table-head">Trang thai</div>
              <div className="table-head">Thao tac</div>
              {users.map((user) => (
                <div className="row" key={user.id}>
                  <div>
                    <div className="user-name">{user.name}</div>
                    <div className="user-email">{user.email}</div>
                  </div>
                  <div>
                    <span className="pill">{user.role}</span>
                  </div>
                  <div>
                    <span className={`status ${user.status}`}>
                      {user.status}
                    </span>
                  </div>
                  <div>
                    <button
                      className="ghost"
                      type="button"
                      onClick={() => handleDelete(user.id)}>
                      Xoa
                    </button>
                  </div>
                </div>
              ))}
            </div>
          }
        </section>
      </main>
    </div>
  );
}

export default App;
