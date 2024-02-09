import java.util.Random;

public class SoccerMatch
{

    private String team0 = "Dream Team";
    private String team1 = "Old Boys";

    public void startMatch()
    {
        System.out.println("Match starting \n\n");
        Random random = new Random();
        for (int i = 0; i < 90; i++)
        {

            int rand = random.nextInt(100);
            int whichTeam = random.nextInt(2);

            if (rand < 8)
            {
                // score goal
                scoreGoal(whichTeam);
            }
            else if (rand < 12)
            {
                // penalty
                roughTackle(whichTeam);
            }

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }

        System.out.println("\n\nMatch ended");
    }

    private void roughTackle(int whichTeam)
    {
        if (whichTeam == 0)
        {
            // TODO team0 made a rough tackle
        }
        else
        {
            // TODO team1 made a rough tackle
        }
    }

    private void scoreGoal(int whichTeam)
    {
        if (whichTeam == 0)
        {
            // TODO team0 scored
        }
        else
        {
            // TODO team1 scored
        }
    }

}
