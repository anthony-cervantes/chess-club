package org.chesscorp.club.persistence;

import org.chesscorp.club.model.stats.ChessClubPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPositionRepository extends JpaRepository<ChessClubPosition, String> {

    ChessClubPosition findOneByText(String position);
}
