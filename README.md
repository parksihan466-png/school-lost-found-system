:root {
    --primary: #1746a2;
    --primary-dark: #0d2d6b;
    --accent: #f59e0b;
    --success: #16a34a;
    --danger: #dc2626;
    --bg: #f5f7fb;
    --text: #1f2937;
    --muted: #6b7280;
    --card: #ffffff;
    --border: #e5e7eb;
    --shadow: 0 10px 25px rgba(15, 23, 42, 0.08);
}

* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, Helvetica, sans-serif;
    background: var(--bg);
    color: var(--text);
}

img {
    max-width: 100%;
    display: block;
}

.container {
    width: min(1200px, calc(100% - 32px));
    margin: 0 auto;
}

.site-header {
    background: var(--primary);
    color: white;
}

.nav {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 18px 0;
}

.brand {
    font-size: 1.3rem;
    font-weight: 700;
}

nav {
    display: flex;
    gap: 18px;
    flex-wrap: wrap;
    align-items: center;
}

nav a {
    color: white;
    text-decoration: none;
    opacity: 0.9;
}

.hero {
    background: linear-gradient(135deg, #dfeeff, #ffffff);
    padding: 72px 0;
}

.hero-grid {
    display: grid;
    grid-template-columns: 1.3fr 0.7fr;
    gap: 24px;
    align-items: center;
}

.eyebrow {
    text-transform: uppercase;
    letter-spacing: 1px;
    color: var(--primary);
    font-size: 0.8rem;
    font-weight: 700;
}

.hero h1 {
    font-size: clamp(2.2rem, 5vw, 4rem);
    margin: 10px 0;
}

.hero p {
    color: var(--muted);
    line-height: 1.7;
}

.cta-row {
    display: flex;
    gap: 12px;
    margin-top: 24px;
    flex-wrap: wrap;
}

.button {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    border: none;
    border-radius: 10px;
    padding: 12px 18px;
    font-weight: 600;
    cursor: pointer;
    text-decoration: none;
}

.button.primary {
    background: var(--primary);
    color: white;
}

.button.secondary {
    background: white;
    border: 1px solid var(--border);
    color: var(--text);
}

.full {
    width: 100%;
}

.hero-card {
    display: grid;
    gap: 14px;
}

.stat-card {
    background: white;
    padding: 22px 18px;
    border-radius: 16px;
    box-shadow: var(--shadow);
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.stat-card strong {
    font-size: 2rem;
    color: var(--primary);
}

.section {
    padding: 50px 0;
}

.card-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 22px;
    margin-top: 24px;
}

.item-card {
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 18px;
    overflow: hidden;
    box-shadow: var(--shadow);
}

.item-card img {
    height: 220px;
    width: 100%;
    object-fit: cover;
}

.item-body {
    padding: 18px;
}

.item-body h3 {
    margin: 8px 0;
}

.item-body p {
    color: var(--muted);
    margin-bottom: 10px;
}

.item-body a {
    color: var(--primary);
    font-weight: 600;
    text-decoration: none;
}

.chip {
    display: inline-block;
    padding: 6px 10px;
    border-radius: 999px;
    font-size: 0.7rem;
    font-weight: 700;
    letter-spacing: 0.04em;
    background: #dbeafe;
    color: var(--primary);
}

.chip.lost {
    background: #fee2e2;
    color: #b91c1c;
}

.chip.found {
    background: #dcfce7;
    color: #15803d;
}

.auth-shell {
    min-height: 100vh;
    display: grid;
    place-items: center;
    padding: 24px;
    background: linear-gradient(135deg, #eef5ff, #f9fbff);
}

.auth-card {
    width: min(480px, 100%);
    background: white;
    border-radius: 22px;
    box-shadow: var(--shadow);
    padding: 32px;
}

.auth-card h1 {
    margin-top: 0;
}

.form-card {
    background: white;
    border: 1px solid var(--border);
    border-radius: 18px;
    box-shadow: var(--shadow);
    padding: 24px;
}

.form-page {
    max-width: 700px;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 18px;
}

label {
    font-weight: 600;
}

input, textarea {
    width: 100%;
    border: 1px solid var(--border);
    border-radius: 10px;
    padding: 12px 14px;
    font: inherit;
}

textarea {
    resize: vertical;
}

.dashboard-grid {
    display: grid;
    grid-template-columns: repeat(3, minmax(180px, 1fr));
    gap: 20px;
    margin: 24px 0;
}

.stat-box {
    background: white;
    padding: 24px;
    border: 1px solid var(--border);
    border-radius: 16px;
    box-shadow: var(--shadow);
}

.stat-box strong {
    font-size: 2rem;
    color: var(--primary);
}

.table-wrapper {
    margin-top: 30px;
    background: white;
    border: 1px solid var(--border);
    border-radius: 16px;
    box-shadow: var(--shadow);
    overflow: hidden;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    text-align: left;
    padding: 14px 16px;
    border-bottom: 1px solid var(--border);
}

th {
    background: #f8fafc;
}

.detail-layout {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 26px;
    align-items: start;
}

.detail-image img {
    width: 100%;
    border-radius: 18px;
    box-shadow: var(--shadow);
}

.detail-content {
    background: white;
    border: 1px solid var(--border);
    border-radius: 18px;
    box-shadow: var(--shadow);
    padding: 24px;
}

.claim-form {
    margin-top: 22px;
}

.alert {
    padding: 12px 14px;
    border-radius: 10px;
    margin-bottom: 18px;
}

.alert.success {
    background: #dcfce7;
    color: #166534;
}

.alert.error {
    background: #fee2e2;
    color: #991b1b;
}

.info-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 18px;
}

.info-box {
    background: white;
    border: 1px solid var(--border);
    border-radius: 16px;
    padding: 24px;
    box-shadow: var(--shadow);
}

.note, .auth-link {
    color: var(--muted);
    text-align: center;
}

.site-footer {
    background: #111827;
    color: white;
    padding: 24px 0;
    margin-top: 30px;
}

@media (max-width: 760px) {
    .hero-grid,
    .detail-layout,
    .info-grid,
    .dashboard-grid {
        grid-template-columns: 1fr;
    }

    .nav {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
    }
}
