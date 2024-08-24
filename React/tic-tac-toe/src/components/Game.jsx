import { useState } from "react";
import Board from "./Board";

export default function Game() {

    const [x, setX] = useState(false)
    const [history, setHistory] = useState([Array(9).fill(null)])

    return (
        <>
            <div className="game">
                <div className='board'>
                    <Board />
                </div>
            </div>
        </>
    )
}
