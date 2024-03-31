package football.ca2;
import javax.persistence.*;
@Entity
@DiscriminatorValue(value="striker")

public class striker extends footballers 
{
	@Column(name = "goals")
	private int goals;
	public int getGoals() 
	{
		return goals;
	}
	
	public void setGoals(int goals) 
	{
		this.goals = goals;
		}
}
