document.addEventListener("DOMContentLoaded", () => {
    // DOM элементы
    const trackListBlock = document.getElementById("trackList");
    const audio = document.getElementById("audio");
    const audioSrc = document.getElementById("audioSource");
    const progress = document.getElementById("progress");
    const progressFill = document.getElementById("progressFill");
    const volume = document.getElementById("volume");
    const volumeFill = document.getElementById("volumeFill");
    const playPauseBtn = document.getElementById("playPauseBtn");
    const currentTimeSpan = document.getElementById("currentTime");
    const totalTimeSpan = document.getElementById("totalTime");
    const bpTitle = document.getElementById("bp-title");
    const bpArtist = document.getElementById("bp-artist");
    const bpImg = document.getElementById("bp-img");
    const prevBtn = document.querySelector(".prev");
    const nextBtn = document.querySelector(".next");
    const shuffleBtn = document.querySelector(".shuffle");
    const repeatBtn = document.querySelector(".repeat");

    // Состояние
    let tracks = [];
    let currentTrackIndex = -1;
    let isPlaying = false;
    let shuffleMode = false;
    let repeatMode = false;
    let originalTracks = [];

    // Иконки
    const playIcon = '<i class="fas fa-play"></i>';
    const pauseIcon = '<i class="fas fa-pause"></i>';

    // Загрузка треков
    fetch("/api/tracks")
        .then(r => r.json())
        .then(data => {
            tracks = data;
            originalTracks = [...data];
            renderTracks();
        })
        .catch(error => {
            console.error("Error loading tracks:", error);
            // Демо-данные для тестирования
            tracks = [
                { id: 1, title: "Technologic", artist: { name: "Daft Punk" }, duration: 245, url: "/audio/technologic.mp3" },
                { id: 2, title: "Around the World", artist: { name: "Daft Punk" }, duration: 367, url: "/audio/around-the-world.mp3" },
                { id: 3, title: "Harder Better Faster", artist: { name: "Daft Punk" }, duration: 224, url: "/audio/harder-better-faster.mp3" },
                { id: 4, title: "One More Time", artist: { name: "Daft Punk" }, duration: 320, url: "/audio/one-more-time.mp3" },
            ];
            renderTracks();
        });

    // Отрисовка треков
    function renderTracks() {
        trackListBlock.innerHTML = "";

        tracks.forEach((t, i) => {
            const trackRow = document.createElement("div");
            trackRow.className = "track-row";
            trackRow.dataset.index = i;

            trackRow.innerHTML = `
                <img src="/img/cover.png" class="track-cover" alt="Cover">
                <div class="track-info">
                    <div class="track-name">${t.title}</div>
                    <div class="track-artist">${t.artist ? t.artist.name : "Unknown"}</div>
                </div>
                <div class="track-time">${formatTime(t.duration)}</div>
                <button class="track-play-btn" onclick="playTrack(${i})">
                    <i class="fas fa-play"></i>
                </button>
            `;

            trackListBlock.appendChild(trackRow);
        });
    }

    // Воспроизведение трека
    window.playTrack = function(index) {
        const t = tracks[index];

        if (currentTrackIndex === index && isPlaying) {
            pauseTrack();
            return;
        }

        if (currentTrackIndex === index) {
            playTrack();
            return;
        }

        currentTrackIndex = index;

        // Обновляем источник аудио
        audioSrc.src = t.url || "/audio/sample.mp3";
        audio.load();

        // Обновляем UI
        updatePlayerInfo(t);
        highlightActiveTrack(index);

        // Воспроизводим
        playTrack();
    };

    function playTrack() {
        audio.play()
            .then(() => {
                isPlaying = true;
                updatePlayPauseButton();

                // Анимация для активного трека
                const activeRow = document.querySelector(`.track-row[data-index="${currentTrackIndex}"]`);
                if (activeRow) {
                    const btn = activeRow.querySelector(".track-play-btn i");
                    if (btn) btn.className = "fas fa-pause";
                }
            })
            .catch(error => {
                console.error("Playback error:", error);
            });
    }

    function pauseTrack() {
        audio.pause();
        isPlaying = false;
        updatePlayPauseButton();

        const activeRow = document.querySelector(`.track-row[data-index="${currentTrackIndex}"]`);
        if (activeRow) {
            const btn = activeRow.querySelector(".track-play-btn i");
            if (btn) btn.className = "fas fa-play";
        }
    }

    function updatePlayerInfo(track) {
        bpTitle.innerText = track.title;
        bpArtist.innerText = track.artist ? track.artist.name : "Unknown";
        bpImg.src = "/img/cover.png";

        // Обновляем информацию в панели текста
        document.querySelector(".now-playing-title").innerText = track.title;
        document.querySelector(".now-playing-artist").innerText = track.artist ? track.artist.name : "Unknown";
    }

    function highlightActiveTrack(index) {
        document.querySelectorAll(".track-row").forEach(r => r.classList.remove("active-track"));
        const activeRow = document.querySelector(`.track-row[data-index="${index}"]`);
        if (activeRow) {
            activeRow.classList.add("active-track");
        }
    }

    function updatePlayPauseButton() {
        const icon = playPauseBtn.querySelector("i");
        if (icon) {
            icon.className = isPlaying ? "fas fa-pause" : "fas fa-play";
        }
    }

    // Обработчики плеера
    playPauseBtn.addEventListener("click", () => {
        if (currentTrackIndex === -1 && tracks.length > 0) {
            playTrack(0);
        } else if (isPlaying) {
            pauseTrack();
        } else {
            playTrack();
        }
    });

    prevBtn.addEventListener("click", () => {
        if (currentTrackIndex > 0) {
            playTrack(currentTrackIndex - 1);
        } else if (tracks.length > 0) {
            playTrack(tracks.length - 1);
        }
    });

    nextBtn.addEventListener("click", () => {
        if (currentTrackIndex < tracks.length - 1) {
            playTrack(currentTrackIndex + 1);
        } else if (tracks.length > 0) {
            playTrack(0);
        }
    });

    shuffleBtn.addEventListener("click", () => {
        shuffleMode = !shuffleMode;
        shuffleBtn.style.color = shuffleMode ? "var(--accent)" : "";

        if (shuffleMode) {
            // Перемешиваем треки
            tracks = [...originalTracks].sort(() => Math.random() - 0.5);
        } else {
            // Возвращаем оригинальный порядок
            tracks = [...originalTracks];
        }

        renderTracks();

        // Обновляем индекс текущего трека
        if (currentTrackIndex !== -1) {
            const currentTrack = originalTracks[currentTrackIndex];
            const newIndex = tracks.findIndex(t => t.id === currentTrack.id);
            if (newIndex !== -1) {
                currentTrackIndex = newIndex;
                highlightActiveTrack(newIndex);
            }
        }
    });

    repeatBtn.addEventListener("click", () => {
        repeatMode = !repeatMode;
        repeatBtn.style.color = repeatMode ? "var(--accent)" : "";
    });

    // Прогресс бар
    audio.addEventListener("timeupdate", () => {
        if (!audio.duration) return;

        const percent = (audio.currentTime / audio.duration) * 100;
        progress.value = percent;
        progressFill.style.width = percent + "%";

        currentTimeSpan.textContent = formatTime(audio.currentTime);
    });

    audio.addEventListener("loadedmetadata", () => {
        totalTimeSpan.textContent = formatTime(audio.duration);
    });

    progress.addEventListener("input", () => {
        if (!audio.duration) return;

        const percent = progress.value;
        audio.currentTime = (percent / 100) * audio.duration;
        progressFill.style.width = percent + "%";
    });

    // Громкость
    volume.addEventListener("input", () => {
        const value = volume.value * 100;
        audio.volume = volume.value;
        volumeFill.style.width = value + "%";

        // Обновляем иконку громкости
        const volumeIcon = document.querySelector(".volume-btn i");
        if (volume.value == 0) {
            volumeIcon.className = "fas fa-volume-mute";
        } else if (volume.value < 0.5) {
            volumeIcon.className = "fas fa-volume-down";
        } else {
            volumeIcon.className = "fas fa-volume-up";
        }
    });

    volume.value = 0.7;
    volumeFill.style.width = "70%";

    // Кнопка громкости (mute)
    const volumeBtn = document.querySelector(".volume-btn");
    let lastVolume = 0.7;

    volumeBtn.addEventListener("click", () => {
        if (audio.volume > 0) {
            lastVolume = audio.volume;
            audio.volume = 0;
            volume.value = 0;
            volumeFill.style.width = "0%";
            volumeBtn.querySelector("i").className = "fas fa-volume-mute";
        } else {
            audio.volume = lastVolume;
            volume.value = lastVolume;
            volumeFill.style.width = (lastVolume * 100) + "%";
            updateVolumeIcon(lastVolume);
        }
    });

    function updateVolumeIcon(vol) {
        const icon = volumeBtn.querySelector("i");
        if (vol == 0) {
            icon.className = "fas fa-volume-mute";
        } else if (vol < 0.5) {
            icon.className = "fas fa-volume-down";
        } else {
            icon.className = "fas fa-volume-up";
        }
    }

    // Автоматическое воспроизведение следующего трека
    audio.addEventListener("ended", () => {
        if (repeatMode) {
            // Повтор текущего трека
            audio.currentTime = 0;
            playTrack();
        } else if (currentTrackIndex + 1 < tracks.length) {
            playTrack(currentTrackIndex + 1);
        } else if (!repeatMode) {
            // Конец плейлиста
            isPlaying = false;
            updatePlayPauseButton();
        }
    });

    // Лайк
    const likeBtn = document.querySelector(".like-btn");
    likeBtn.addEventListener("click", () => {
        const icon = likeBtn.querySelector("i");
        icon.className = icon.className.includes("far") ? "fas fa-heart" : "far fa-heart";
        icon.style.color = icon.className.includes("fas") ? "var(--accent)" : "";
    });

    // Форматирование времени
    function formatTime(seconds) {
        if (!seconds || isNaN(seconds)) return "0:00";
        const mins = Math.floor(seconds / 60);
        const secs = Math.floor(seconds % 60);
        return mins + ":" + (secs < 10 ? "0" + secs : secs);
    }

    // Закрытие панели текста
    document.querySelector(".close-lyrics").addEventListener("click", () => {
        document.querySelector(".lyrics-panel").style.display = "none";
        document.querySelector(".layout").style.gridTemplateColumns = "280px 1fr 0";
    });

    // Обработка ошибок аудио
    audio.addEventListener("error", (e) => {
        console.error("Audio error:", e);
        alert("Error playing audio. Please try again.");
    });
});