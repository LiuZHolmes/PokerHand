#### PokerHand
1. getCardNumberByString
    - input: "5"
    - output: CardNumber.FIVE
2. getCardTypeByString
    - input: "D"
    - output: CardType.Diamond
3. getCardByString
    - input: "5D"
    - output: Diamond FIVE
4. getAHandBy5Cards
    - input: [Card]
    - output: Hand
5. getHandByCards
    - input: "2D 3D 4D 5D 7S 7D 8D 9D JD QS"
    - output: [Hand]
 ---
#### Hand
1. sortHand
    - input: 2D 4S 3D 6S 5H
    - output: 2D 3D 4S 5H 6S
2. calHandLevelAndAce
    - input: 2D 3D 5S 6H 7S
    - output: {ace: SEVEN, level: 0}
2. tryPair
    1. not match
        - input: 2D 3D 5S 6H 7S
        - output: null
    2. match
            - input: 2D 2H 5S 6H 7S
            - output: {ace: SEVEN, level: 1}