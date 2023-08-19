package com.github.minsoozz.order.adapter.out.persistence.outbox;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "outbox")
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;

    private String aggregateId;

    private String eventType;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private JsonNode payload;

    public OutboxJpaEntity() {

    }

    public OutboxJpaEntity(String aggregateType, String aggregateId, String eventType, JsonNode payload) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    @Override
    public String toString() {
        return "OutboxJpaEntity{" +
                "id=" + id +
                ", aggregateType='" + aggregateType + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", payload=" + payload +
                '}';
    }
}
