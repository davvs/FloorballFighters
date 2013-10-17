package se.davvs.floorballfighters.models;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="day")
public class Day {
	    @Id
	    @Column(name="id")
	    @GeneratedValue
	    private Integer id;
	     
	    @Column(name="date")
	    private Date date;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
}
