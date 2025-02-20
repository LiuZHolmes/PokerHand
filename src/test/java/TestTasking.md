#### entity.PokerHand
1. getCardNumberByString
    - input: "5"
    - output: constant.CardNumber.FIVE
2. getCardTypeByString
    - input: "D"
    - output: constant.CardType.Diamond
3. getCardByString
    - input: "5D"
    - output: Diamond FIVE
4. getAHandBy5Cards
    - input: [entity.Card]
    - output: entity.Hand
5. getHandByCards
    - input: "2D 3D 4D 5D 7S 7D 8D 9D JD QS"
    - output: [entity.Hand]
6. compareLevel
    1. 
        - input: HIGHCARD PAIR
        - output: negative
    2. 
        - input: THREEOFAKIND PAIR
        - output: positive
7. compareCard
    1. 
        - input: 5D 2S
        - output: positive
8. compareRemainHand
    1. 
        - input: [2D 3D 5S 6H] [2D 4D 5S 6H]
        - output: negative
    2. 
        - input: [2D 4D 5S 6H] [2D 3D 5S 6H] 
        - output: positive
9. compareHand
    1. 
        - input: [2D 3D 4D 5D 7S] [7D 8D 9D JD QS]
        - output: negative
    2. 
        - input: [2D 2H 2S JD QS] [3D 3H 4D 4S 7S]
        - output: positive
    3. 
        - input: [2D 2H 3S 3D QS] [2S 2C 3C 3H 7S]
        - output: positive
10. getWinner
    1. 
        - input: 0
        - output: "tied"
    2. 
        - input: positive
        - output: "Player 1 wins!"
    3. 
        - input: negative
        - output: "Player 2 wins!"
11. game
    - input: "2D 2H 2S JD QS 3D 3H 4D 4S 7S"
    - output: "Player 1 wins!"
 ---
#### entity.Hand
1. sortHand
    - input: 2D 4S 3D 6S 5H
    - output: 2D 3D 4S 5H 6S
2. calHandLevelAndAce
    1. unsorted
        - input: 2D 5S 3D 7S 6H
        - output: {ace: SEVEN, level: 0}
    2. high card
        - input: 2D 3D 5S 6H 7S
        - output: {ace: SEVEN, level: 0}
    3. pair
        - input: 2D 2H 5S 6H 7S
        - output: {ace: TWO, level: 1}
    4. twoPairs
        - input: 2D 2H 5S 5H 7S
        - output: {ace: FIVE, level: 2}
    5. threeOfAKind
        - input: 2D 2H 2S 5H 7S
        - output: {ace: TWO, level: 3}
    5. tryStraight
        - input: 2D 3H 4S 5H 6S
        - output: {ace: SIX, level: 4}
2. tryPair
    1. not match
        - input: 2D 3D 5S 6H 7S
        - output: null
    2. match
        - input: 2D 2H 5S 6H 7S
        - output: {ace: 2H, level: 1}
3. tryTwoPairs
    1. not match
        - input: 2D 3D 5S 6H 7S
        - output: null
    2. match
        - input: 2D 2H 5S 5H 7S
        - output: {ace: 5S, secondAce: 2H, level: 2}
4. tryThreeOfAKind
    1. not match
        - input: 2D 3D 5S 6H 7S
        - output: null
    2. match
        - input: 2D 2H 2S 5H 7S
        - output: {ace: 2S, level: 3}
4. tryStraight
    1. not match
        - input: 2D 3D 5S 6H 7S
        - output: null
    2. match
        - input: 2D 3H 4S 5H 6D
        - output: {ace: 6D, level: 4}
4. calRemainHand
    1. high card
        - input: 2D 3D 5S 6H 7S
        - output: 2D 3D 5S 6H
    2. pair
        - input: 2D 2H 5S 6H 7S
        - output: 5S 6H 7S
    3. twoPairs
        - input: 2D 2H 5S 5H 7S
        - output: 7S
    4. threeOfAKind
        - input: 2D 2H 2S 5H 7S
        - output: 5H 7S
    4. straight
        - input: 2D 3H 4S 5H 6S
        - output: 2D 3H 4S 5H
5. calHandPower
    1. high card
        - input: 2D 5S 3D 7C 6H
        - output: {ace: 7C, level: 0, remainHand:[]}