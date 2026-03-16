package player.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import player.models.Track;
import player.repositories.TrackRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Track getTrackById(Long id) {
        Optional<Track> track = trackRepository.findById(id);
        return track.orElse(null);
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

}