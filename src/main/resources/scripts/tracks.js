document.addEventListener("DOMContentLoaded", () => {
    fetch("/api/tracks")
        .then(r => r.json())
        .then(data => {
            const block = document.getElementById("tracks");
            data.forEach(t => {
                block.innerHTML += `
                    <p>${t.title} — ${t.artist ? t.artist.name : "Unknown"}</p>
                `;
            });
        });
});