# Tomasulo Algorithm Simulator

A cycle-by-cycle simulator of Tomasulo's algorithm in Java. It reads a short MIPS-style instruction file, issues the instructions into reservation stations, tracks register renaming and operand readiness, and advances a clock until every instruction has written its result.

Written as a computer architecture course project.

## What it models

- **Instruction queue** — instructions are issued in order as a station frees up
- **Reservation stations** — hold the operation, operand values and the tags of results they are still waiting on
- **Register file** — each register carries a value plus the tag of the station that will produce it, which is how write-after-write and read-after-write hazards are resolved
- **Execution timing** — per-operation latencies, configured in `main`: `ADD.D` 4 cycles, `SUB.D` 3, `MUL.D` 6, `DIV.D` 8
- **Common data bus** — one result is broadcast per cycle; waiting stations capture it by tag
- **Clock loop** — runs until the instruction queue is drained and no execution times remain outstanding

## Supported instructions

| Category | Operations |
| --- | --- |
| Floating point / ALU | `ADD.D`, `SUB.D`, `MUL.D`, `DIV.D`, `ADD.I`, `SUB.I` |
| Memory | `LW`, `SW` |
| Branch | `BNEZ` |

`InstructionParser` splits each line, normalises the operation to upper case, and builds either an `ALUInstruction` or a `MemInstruction`. Memory operands are parsed in `offset(register)` form.

## Project layout

```
src/micro/
  TomasuloSimulator.java   Entry point, clock loop, issue and write-back
  InstructionParser.java   Line parsing and instruction categorisation
  Instruction.java         Base instruction type
  ALUInstruction.java      Register-register and immediate operations
  MemInstruction.java      LW and SW, with offset(register) addressing
  InstructionQueue.java    In-order issue queue
  ReservationStation.java  Station array, allocation and tag matching
  RegistersFile.java       Register values and producer tags
  ExecutionTime.java       Per-station execution and write-result cycles
  TimeDetails.java         Execution and write-result pair
  Mips.txt                 The instruction program that gets simulated
```

## Running it

Requires a JDK 9+ (the project declares a `micro` module).

```bash
javac -d bin src/module-info.java src/micro/*.java
java -p bin -m micro/micro.TomasuloSimulator
```

Or open the folder in Eclipse — `.classpath` and `.project` are checked in — and run `TomasuloSimulator`.

## Input and configuration

The program to simulate lives in `src/micro/Mips.txt`, one instruction per line:

```
SUB.D R1, R2, R3
SUB.I R1, R1, 1
BNEZ R1, 0
```

Initial register values and the per-operation latencies are set in `main` rather than read from a file:

```java
x.registersFile.setRegister("R2", "0", 3);
x.registersFile.setRegister("R3", "0", 2);
x.AddClockcycle = 4;
x.SUBClockcycle = 3;
x.MULClockcycle = 6;
x.DIVClockcycle = 8;
```

Edit those lines to simulate a different starting state. `readInstructionsFromFile` accepts either a path relative to the working directory or a bare filename, which it then looks for under `src/micro/`.
