document.addEventListener("DOMContentLoaded", () => {
    fetch("/api/playlists")
        .then(r => r.json())
        .then(data => {
            const block = document.getElementById("playlists");
            data.forEach(p => {
                block.innerHTML += `<p>${p.name}</p>`;
            });
        });
});