package com.fittoo.review.entity;

import com.fittoo.common.entity.DateBaseEntity;
import com.fittoo.member.entity.Member;
import com.fittoo.trainer.entity.Trainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Review extends DateBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    private int score;
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    public Review(String comment,
        int score, Long reservationId) {
        this.comment = comment;
        this.score = score;
        this.reservationId = reservationId;
    }

    public void addMember(Member member) {
        this.member = member;
        member.addReview(this);
    }

    public void addTrainer(Trainer trainer) {
        this.trainer = trainer;
        trainer.addReview(this);
    }
}
