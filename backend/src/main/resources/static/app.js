window.addEventListener('DOMContentLoaded', () => {
    console.log('app.js loaded');
});

const base = '';
let basicToken = null;


function setOut(id, obj, ok = true) {
    const el = document.getElementById(id);
    el.className = ok ? 'ok' : 'err';
    el.textContent = typeof obj === 'string' ? obj : JSON.stringify(obj, null, 2);
}


async function fetchJSON(url, options = {}) {
    const res = await fetch(url, options);
    let data;
    try { 
        data = await res.json(); 
    } catch { 
        data = await res.text(); }
    if (!res.ok) throw { status: res.status, data };
    return data;
}


window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('btn_reg').addEventListener('click', onRegister);
    document.getElementById('btn_auth').addEventListener('click', onAuth);
    document.getElementById('btn_listbooks').addEventListener('click', onListBooks);
    document.getElementById('btn_searchbook').addEventListener('click', onSearch);
    document.getElementById('btn_borrow').addEventListener('click', onBorrow);
    document.getElementById('btn_return').addEventListener('click', onReturn);
    document.getElementById('btn_history').addEventListener('click', onHistory);
});


async function onRegister() {
    const body = {
        username: document.getElementById('reg_username').value.trim(),
        email: document.getElementById('reg_email').value.trim(),
        password: document.getElementById('reg_password').value
    };

    if (!body.username || !body.email || !body.password) {
        setOut('reg_out', 'Please fill all blanks', false);
        return;
    }

    try {
        const data = await fetchJSON(`${base}/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
    });
    setOut('reg_out', data, true);
    } catch (e) {
        setOut('reg_out', e.data || e.status || String(e), false);
    }
}


function onAuth() {
    const u = document.getElementById('auth_username').value.trim();
    const p = document.getElementById('auth_password').value;

    if (!u || !p) {
        document.getElementById('auth_status').textContent = 'Please fill all blanks';
        return;
    }


    basicToken = btoa(unescape(encodeURIComponent(`${u}:${p}`)));
    document.getElementById('auth_status').textContent = `login for ${u}`;
}


async function onListBooks() {
    try {
        const data = await fetchJSON(`${base}/api/books`);
        setOut('search_out', data, true);
    } catch (e) {
        setOut('search_out', e.data || e.status || String(e), false);
    }
}


async function onSearch() {
    const q = encodeURIComponent(document.getElementById('q').value.trim());
    try {
        const data = await fetchJSON(`${base}/api/books?q=${q}`);
        setOut('search_out', data, true);
    } catch (e) {
        setOut('search_out', e.data || e.status || String(e), false);
    }
}


async function onBorrow() {
    const body = {
        userId: Number(document.getElementById('borrow_userid').value),
        bookId: Number(document.getElementById('borrow_bookid').value)
    };

    if (!basicToken) {
        setOut('bandr_out', 'Please login first', false);
        return;
    }

    try {
        const data = await fetchJSON(`${base}/api/borrow`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${basicToken}`
        },
        body: JSON.stringify(body)
        });
        setOut('bandr_out', data, true);
    } catch (e) {
        setOut('bandr_out', e.data || e.status || String(e), false);
    }
}


async function onReturn() {
    const body = { recordId: Number(document.getElementById('return_recordid').value) };

    if (!basicToken) {
        setOut('bandr_out', 'Please login first', false);
        return;
    }

    try {
        const data = await fetchJSON(`${base}/api/return`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${basicToken}`
        },
        body: JSON.stringify(body)
        });
        setOut('bandr_out', data, true);
    } catch (e) {
        setOut('bandr_out', e.data || e.status || String(e), false);
    }
}


async function onHistory() {
    const userid = Number(document.getElementById('history_userid').value);

    if (!basicToken) {
        setOut('history_out', 'Please login first', false);
        return;
    }

    try {
        const data = await fetchJSON(`${base}/api/borrows?userId=${userid}`, {
        headers: { 'Authorization': `Basic ${basicToken}` },
        });
        setOut('history_out', data, true);
    } catch (e) {
    setOut('history_out', e.data || e.status || String(e), false);
    }
}
