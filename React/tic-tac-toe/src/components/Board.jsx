import { useState } from "react";
import Square from "./Square";
import calculateWinner from "./calculateWinner";
import GameOver from "./GameOver";

export default function Board() {

    const [x, setX] = useState(false)
    const [squares, setSquares] = useState(Array(9).fill(null))

    function handleClick(i) {
        if (squares[i] || calculateWinner(squares)) return
        const nextSquares = squares.slice();
        if (x) nextSquares[i] = 'X'
        else nextSquares[i] = 'O'
        setSquares(nextSquares)
        setX(!x)
    }

    const winner = calculateWinner(squares)
    let status;
    if (winner) status = "Winner: " + winner
    else if (GameOver(squares)) status = "Game Over"
    else status = "Next Move: " + (x ? "X" : "O")

    return (
        <>
            <h1>{status}</h1>
            <div className='board-row'>
                <Square value={squares[0]} onSquareClick={() => handleClick(0)} />
                <Square value={squares[1]} onSquareClick={() => handleClick(1)} />
                <Square value={squares[2]} onSquareClick={() => handleClick(2)} />
            </div>
            <div className='board-row'>
                <Square value={squares[3]} onSquareClick={() => handleClick(3)} />
                <Square value={squares[4]} onSquareClick={() => handleClick(4)} />
                <Square value={squares[5]} onSquareClick={() => handleClick(5)} />
            </div>
            <div className='board-row'>
                <Square value={squares[6]} onSquareClick={() => handleClick(6)} />
                <Square value={squares[7]} onSquareClick={() => handleClick(7)} />
                <Square value={squares[8]} onSquareClick={() => handleClick(8)} />
            </div>
        </>
    )
}
