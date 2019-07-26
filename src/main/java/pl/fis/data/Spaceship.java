package pl.fis.data;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spaceship extends ResourceSupport
{
	@NotBlank
	private String name;

	@Positive
	private double speed;

	@PastOrPresent
	@JsonProperty("year-of-manufacturing")
	private LocalDate dayOfManufacture;

	public Spaceship()
	{
	};

	public Spaceship(String name)
	{
		this.name = name;
	}

	public Spaceship(String name, double speed, String date)
	{
		this.name = name;
		this.speed = speed;
		this.dayOfManufacture = LocalDate.parse(date);
	}

	public LocalDate getDayOfManufacture()
	{
		return dayOfManufacture;
	}

	public void setDayOfManufacture(LocalDate dayOfManufacture)
	{
		this.dayOfManufacture = dayOfManufacture;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
