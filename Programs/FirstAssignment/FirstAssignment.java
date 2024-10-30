import java.awt.Font;
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;

public class FirstAssignment extends GraphicsProgram {

    public void run() {
        // The font to be used
        Font f = new Font("Serif", Font.BOLD, 18);
        
        // Line 1
        GLabel s1 = new GLabel("What I did on my summer vacation ...", 10, 20);
        s1.setFont(f);
        add(s1);
        
        GLabel l1 = new GLabel("In the beginning of June, I spent my time hanging out with friends until June 23rd.", 10, 40);
        l1.setFont(f);
        add(l1);
        
        GLabel l2 = new GLabel("On June 23rd, I was on the plane to go to India. The journey went smoothly,", 10, 60);
        l2.setFont(f);
        add(l2);
        
        GLabel l3 = new GLabel("and before I knew it, I was in India. I was in awe because I hadn't been there in 7 years,", 10, 80);
        l3.setFont(f);
        add(l3);
        
        GLabel l4 = new GLabel("so all the memories came flooding back. We went to our house in India and had a good rest.", 10, 100);
        l4.setFont(f);
        add(l4);
        
        GLabel l5 = new GLabel("The first few days I was jetlagged, so I spent all day asleep. After I got over the jetlag,", 10, 120);
        l5.setFont(f);
        add(l5);
        
        GLabel l6 = new GLabel("I finally had time to hang out with my cousins. In India, they use motorbikes instead of cars,", 10, 140);
        l6.setFont(f);
        add(l6);
        
        GLabel l7 = new GLabel("so going out on the bike was very fun. I even got to drive it. Hanging out with my cousins was great.", 10, 160);
        l7.setFont(f);
        add(l7);
        
        GLabel l8 = new GLabel("After that, we went on a couple of trips in India, but most were very boring.", 10, 180);
        l8.setFont(f);
        add(l8);
        
        GLabel l9 = new GLabel("One of the memorable trips was to a famous temple where Sai Baba spent his time.", 10, 200);
        l9.setFont(f);
        add(l9);
        
        GLabel l10 = new GLabel("Sai Baba is worshipped in Hinduism, so it was cool to visit this place of origin.", 10, 220);
        l10.setFont(f);
        add(l10);
        
        GLabel l11 = new GLabel("However, I had a rough time because I was sick, and it was raining heavily. Also,", 10, 240);
        l11.setFont(f);
        add(l11);
        
        GLabel l12 = new GLabel("the toilets wouldn't flush, so it was hard to use the bathroom in the hotel. Once we checked", 10, 260);
        l12.setFont(f);
        add(l12);

        GLabel l13 = new GLabel("into a fancy hotel, it felt like I was back in America. India was fun, but all things must end.", 10, 280);
        l13.setFont(f);
        add(l13);

        GLabel l14 = new GLabel("We returned to America, where I was jetlagged again. Once back, I wasted no time getting to work,", 10, 300);
        l14.setFont(f);
        add(l14);
        
        GLabel l15 = new GLabel("starting on an app with my friend. I've been working on it for about 2 weeks. That's how I spent my summer.", 10, 320);
        l15.setFont(f);
        add(l15);
    }
}
