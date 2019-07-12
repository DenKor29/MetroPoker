import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DeckOfCard {

    private Card[] deck;



    private Card   rubashka;
    private Card[] priz;

    private  String suits[] = {"Треф","Бубей","Червей","Пик"};
    private  String faces[] = {"2","3","4","5","6","7","8","9","10","Валет","Дама","Король","Туз"};
    private  String combinations[] = {"Флэш Рояль","Стрит Флэш","Каре","Фулл Хаус","Флеш","Стрит","Сет (тройка)","Две пары","Пара","Старшая карта"};

    final int width = 73;
    final int height = 115;
    final int cols = 13;
    final int rows = 4;

    final int count = cols*rows;

    BufferedImage bigImage;
    BufferedImage bigImage2;

    protected int RoyalFlush    = 0;
    protected int StraightFlush = 1;
    protected int Four          = 2;
    protected int FullHouse     = 3;
    protected int Flush         = 4;
    protected int Straight      = 5;
    protected int Three         = 6;
    protected int TwoPairs      = 7;
    protected int Pair          = 8;
    protected int Blank         = 9;


    public int getCount() {
        return count;
    }

    public Card getRubashka() {
        return rubashka;
    }


    public DeckOfCard(String filename,String filename2) {

        this.deck = new Card[count];
        this.bigImage = getBufferedImage(filename);
        this.bigImage2 = getBufferedImage(filename2);
        this.priz = new Card[10];

        for (int suit = 0; suit <rows ; suit++) {
            for (int num = 0; num <cols; num++) {

            BufferedImage image = bigImage.getSubimage(
                    getCoordinateX(num) ,
                    getCoordinateY(suit) ,
                    getWidth(num),
                    height );

                int  num1 = (num == 0) ? 12 : num-1;
                String name = faces[num1]+" "+ suits[suit];
                deck[suit*cols+num1] = new Card(suit,num1,name,image);
            }
        }
        BufferedImage image2 = bigImage.getSubimage(
                getCoordinateX(2) ,
                getCoordinateY(4) ,
                getWidth(2),
                height );

        this.rubashka = new Card(4,2,"Рубашка",image2);

        //Призы
        for (int i = 0; i < priz.length ; i++) {
            BufferedImage image3 = bigImage2.getSubimage(
                    0,
                    62*i ,
                    144,
                    62 );
            priz[i] = new Card(5,i,combinations[i],image3);

        }

    }
    public Card GetCardPriz(int num){
        return priz[num];
    }

    public  Card getDeckCard(int index) {
        return deck[index];
    }

    private int getCoordinateX(int num){

        if (num == 0) return 0;

        int ret = num*width+num;
        if (num > 5) ret--;
        if (num == 12) ret --;
        return ret;
    }
    private int getWidth(int num){
        int ret = width;
        if (num == 0) ret++;
        if (num == 12) ret++;
        return ret;
    }

    private int getCoordinateY(int num){

        if (num == 0) return 0;
        return num*height;
    }

    private BufferedImage getBufferedImage (String name){
        String filename = "img/" + name + ".png";

        try {
            return ImageIO.read(getClass().getResource(filename));
        } catch (IOException e) {

        }
        return null;
    }

    public Card getRandomCard(int rnd1,int rnd2,int rnd3,int rnd4,int rnd5){

        int rnd  ;

        while (true) {

            rnd = (int) Math.ceil(count * Math.random())-1 ;


            if ((rnd != rnd1) && (rnd != rnd2) &&
                (rnd != rnd3) && (rnd != rnd4) &&  (rnd != rnd5))
            break;

        }

        return deck[rnd];
    }


}
