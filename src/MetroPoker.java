import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MetroPoker extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private DisplayCard displayCard[];
    private JButton buttonCard[];
    private DeckOfCard deck;
    private int countRazdach;
    private int countChange;







    JLabel labelcountRazdach;
    JLabel labelCombination;
    JLabel labelChoiceCard;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MetroPoker("Поймай Стража.");
            }
        });

    }

    public MetroPoker(String s){

        super(s);

        deck = new DeckOfCard("deck161536","priz");
        displayCard = new DisplayCard[5];
        buttonCard = new JButton[5];

        countRazdach = 0;


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setAlwaysOnTop(true);

        setLayout(new BorderLayout(5,5));


        JPanel panel_desk = new JPanel();
        panel_desk.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Игровой стол"));
        panel_desk.setLayout(new GridLayout(3, 1, 5, 5));

        JPanel panel_message = new JPanel();
        panel_message.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Сообщения"));
        panel_message.setLayout(new GridLayout(1, 2, 5, 5));
        panel_message.add(new JLabel("Собрана комбинация:"));
        labelCombination = new JLabel("Начальная позиция.");
        panel_message.add(labelCombination);
        panel_desk.add(panel_message);


        JPanel panel_button = new JPanel();
        panel_button.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Обмен карт"));
        panel_button.setLayout(new GridLayout(1, 5, 5, 5));
        panel_desk.add(panel_button);

        for (int i = 0; i < 5 ; i++) {

            buttonCard[i] = new JButton("Карта "+(i+1));
            buttonCard[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String str = e.getActionCommand().split(" ")[1];

                    int num = Integer.parseInt(str)-1;


                    for (int j = 0; j < buttonCard.length; j++) {
                        if (j!=num)  buttonCard[j].setVisible(false);
                        if ((j==num) && (countChange!=0))  buttonCard[j].setVisible(false);
                    };


                    setDisplayCard(num,getRandomCard());
                    countChange++;
                    setShowCombinations();

                }
            });

            panel_button.add(buttonCard[i]);


        }

        JPanel panel_card = new JPanel();
        panel_card.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Карты"));
        panel_card.setLayout(new GridLayout(1, 5, 5, 5));
        Card card = deck.getRubashka();
        for (int i = 0; i < displayCard.length; i++) {
            displayCard[i] = new DisplayCard(card);
            panel_card.add(displayCard[i].getLabel());
        };

        panel_desk.add(panel_card);





        add(panel_desk,BorderLayout.CENTER);






        JPanel panel_prize = new JPanel();
        panel_prize.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Правила игры"));
        panel_prize.setLayout(new GridLayout(1, 1, 5, 5));
        add(panel_prize,BorderLayout.EAST);

            JLabel prize = new JLabel(getImage("pravila"));
            panel_prize.add(prize);

        JPanel panel_manage = new JPanel();
        panel_manage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Управление"));
        panel_manage.setLayout(new GridLayout(1, 5, 5, 5));
        add(panel_manage,BorderLayout.SOUTH);




        JButton bmanage = new JButton("Старт");
        bmanage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setEmptyDisplayCard(); MetroPoker.this.countRazdach++;MetroPoker.this.setCountRazdach();
                countChange = 0;
                for (int j = 0; j < buttonCard.length; j++) {
                    buttonCard[j].setVisible(true);
                };

                for (int i = 0; i < MetroPoker.this.displayCard.length ; i++) {
                    MetroPoker.this.setDisplayCard(i,getRandomCard());
                }
                setShowCombinations();

            }
        });

        JButton b2manage = new JButton("Поиск ...");
        b2manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setEmptyDisplayCard();
                countChange = 0;

                for (int j = 0; j < buttonCard.length; j++) {
                    buttonCard[j].setVisible(true);
                };


                MetroPoker.this.countRazdach = 0;

                while (true) {
                    MetroPoker.this.countRazdach++;MetroPoker.this.setCountRazdach();

                    for (int i = 0; i < MetroPoker.this.displayCard.length; i++) {
                        MetroPoker.this.setDisplayCard(i, getRandomCard());
                    };

                    Card strazCard = getChoiceFlashStreetCard();
                        if (strazCard != null) break;

                }
                   setShowCombinations();
            }
        });
        panel_manage.add(bmanage);
        panel_manage.add(b2manage);


        labelcountRazdach = new JLabel("Нажмите старт для начала игры.");
        panel_manage.add(labelcountRazdach);


        setIconImage(getImage("icon").getImage());
        //pack();
        setVisible(true);
        setLocationRelativeTo(null);


    }



    private ImageIcon getImage (String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(filename)));
        return icon;
    }

    private void setDisplayCard(int num, Card card) {
        displayCard[num].setCard(card);
    }



    private void setEmptyDisplayCard(){
        Card card = deck.getRubashka();
        for (int i = 0; i < displayCard.length ; i++) {
            setDisplayCard(i,card);
        }

    }

    private Card getRandomCard(){
        int rnd1 = displayCard[0].getCard().getIndex() ;
        int rnd2 = displayCard[1].getCard().getIndex() ;
        int rnd3 = displayCard[2].getCard().getIndex() ;
        int rnd4 = displayCard[3].getCard().getIndex() ;
        int rnd5 = displayCard[4].getCard().getIndex() ;

        return deck.getRandomCard(rnd1,rnd2,rnd3,rnd4,rnd5);
    }

    private void setShowCombinations(){

        Card priz = getPrizeCard();
        labelCombination.setText(priz.getName());
        labelCombination.setIcon(new ImageIcon(priz.getImageFace()));
        labelCombination.setVerticalTextPosition(JLabel.TOP);
        labelCombination.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void setCountRazdach(){
        int n1 = Math.round(countRazdach/7)+1;
        int n2 = (countRazdach%7); if (n1>0) n2++;
        String message = "Уровень:"+n1+ ". Раздача:"+n2+". Всего:"+countRazdach+"." ;
        labelcountRazdach.setText(message);
    }



    public Card getPrizeCard(){
        int ret = getNumPrizeCard();
        if (ret == -1) return null;
        return deck.GetCardPriz(ret);
    }
    public int getNumPrizeCard(){

        int ret = -1;

        //Получим карты со стола
        Card card[]=new Card[5];

        for (int i = 0; i <5 ; i++) {
            card[i] = displayCard[i].getCard();
            card[i].setTypeCompare(0);
        }
        //Отсортируем карты
        Arrays.sort(card);

        int difcards = 1;  //число различных по старшинству карт
        int maxsame  = 0;  //макс. число подряд идущих одинаковых
        int cursame  = 1;  //текущее число одинаковых подряд
        boolean sameSuit = true;

        for (int i = 1; i <= 4 ; i++) {
            if (card[i].getNum() == card[i-1].getNum()) {
                cursame++;
                if (cursame>maxsame) maxsame=cursame;

            }
            else {
             cursame =1;
             sameSuit = sameSuit & (card[i].getSuit() == card[i-1].getSuit());
             difcards++;
            }
        }

        switch (difcards) {
            //если разных карт 5 то под подозрением пустая комбинация, флэш, стрит
            case 5: {
                if ((card[0].getNum() == card[1].getNum() - 1) &&
                    (card[1].getNum() == card[2].getNum() - 1) &&
                       (card[2].getNum() == card[3].getNum() - 1) &&
                            (card[3].getNum() == card[4].getNum() - 1)){
                    //Флеш Стрит или Стрит
                    if (sameSuit) {
                        if (card[0].getNum() == 8) ret = deck.RoyalFlush;
                        else ret = deck.StraightFlush;
                    }
                    else ret = deck.Straight;

                } else {
                    //Флеш или Старшая карта
                    if (sameSuit) ret = deck.Flush; else ret = deck.Blank;

                }

                break;
                }

            //если у нас одна пара, то количество разных ценностей карт как раз 4
            case 4: {
                ret = deck.Pair;
                break;}

            //тройка или две пары
             case 3: {
                 if (maxsame == 3) ret = deck.Three;
                 if (maxsame == 2) ret =deck.TwoPairs;
                 break;}
            //каре или полный дом
             case 2: {
                 if (maxsame == 4) ret = deck.Four;
                 if (maxsame == 3) ret = deck.FullHouse;
                 break;}


        }


        return ret;
    }

    public Card getChoiceFlashStreetCard(){

        int i;

        //Получим карты со стола
        Card card[]=new Card[5];

        for (i = 0; i <5 ; i++) {
            card[i] = displayCard[i].getCard();
            card[i].setTypeCompare(1);
        }
        //Отсортируем карты по масти и номиналу
        Arrays.sort(card);


        boolean NumCard[] = new boolean[4];
        boolean NumCard2[] = new boolean[4];

        int SuitCard[] = new int[4];

        for (i = 0; i <4 ; i++) {
            SuitCard[i] = 0;
        }


        int difcards = 0;  //число различных не подряд

        int maxsame  = 0;  //макс. число подряд идущих одинаковых
        int cursame  = 1;  //текущее число одинаковых подряд


        //Определим веса номиналов и мастей
        for (i = 0; i < 4 ; i++) {

            NumCard[i] = (card[i+1].getNum() == (card[i].getNum()+1));
            NumCard2[i] = (NumCard[i] && (card[i+1].getSuit() == (card[i].getSuit())));


            //Определим  идущие подряд карты по масти и номиналу
            if ( NumCard2[i]) {
                cursame++;
                if (cursame>maxsame) maxsame=cursame;
            }
            else {
                cursame = 1;
                difcards++ ;
            }

        }

        //если 5 карт подряд одной масти - это флеш стрит
        if (maxsame == 5) return deck.getRubashka();

        // Определим маскимальное число карт одной масти
        int maxsuit  = 0;  //макс. число подряд идущих мастей

        for (i = 0; i <5 ; i++) {
            int suit = card[i].getSuit();
            SuitCard[suit]++ ;
                if (SuitCard[suit]>maxsuit) maxsuit=SuitCard[suit];
        }

        //Одной масти должно быть минимум 4 карты
        if (maxsuit <4) return null;

        //4  карты подряд одной масти .
        // Варианты (x+4) или (4+x)
        if (maxsame == 4) {
            // Варианты (x+4) - 1 карта
            if (NumCard2[0] == false) return card[0];
            // Вариант(4+x) - 5 карта
            if (NumCard2[3] == false) return card[4];
            return null;
        }


        //3 карты подряд  - меняем 4 или 2 карту.
        // Варианты (3+x+1) или (1+x+3)
        if (maxsame == 3){

            // Вариант (3+x+1)
            if (NumCard2[0] && NumCard2[1])
                if (card[0].getNum() == (card[4].getNum()-4))
                if (card[0].getSuit() == card[4].getSuit())
            return card[3];

            // Вариант (3+1+x)
            if (NumCard2[0] && NumCard2[1])
                if (card[0].getNum() == (card[3].getNum()-4))
                if (card[0].getSuit() == card[3].getSuit())
                return card[4];

            // Вариант (1+3+x)
            //////////////////////////////////////////////
            if ((NumCard2[1]) && NumCard2[2])
                if (card[0].getNum() == (card[1].getNum()-2))
                if (card[0].getSuit() == card[1].getSuit())
                return card[4];

            ////////////////////////////////////////////


            // Вариант (1+x+3)
            if ((NumCard2[2]) && NumCard2[3])
                if (card[0].getNum() == (card[4].getNum()-4))
                    if (card[0].getSuit() == card[4].getSuit())
                        return card[1];

            // Вариант (x+1+3)
            if ((NumCard2[2]) && NumCard2[3])
                if (card[1].getNum() == (card[4].getNum()-4))
                    if (card[1].getSuit() == card[4].getSuit())
                        return card[0];

            // Вариант (x+3+1)
            if ((NumCard2[1]) && NumCard2[2])
                if (card[1].getNum() == (card[4].getNum()-4))
                    if (card[1].getSuit() == card[4].getSuit())
                        return card[0];
            return null;
        }



        //если 2 карты подряд.
        if (maxsame == 2) {
            // Вариант (2+x+2)
            if ((NumCard2[0]) && NumCard2[3])
                if (card[0].getNum() == (card[4].getNum() - 4))
                    if (card[0].getSuit() == card[4].getSuit())
                        return card[2];
            // Вариант (2+2+x)
            if ((NumCard2[0]) && NumCard2[2])
                if (card[0].getNum() == (card[3].getNum() - 4))
                    if (card[0].getSuit() == card[3].getSuit())
                        return card[4];
            // Вариант (x+2+2)
            if ((NumCard2[1]) && NumCard2[3])
                if (card[1].getNum() == (card[4].getNum() - 4))
                    if (card[1].getSuit() == card[4].getSuit())
                        return card[0];
        }


        return null;
    }
}
