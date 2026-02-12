# Octane Engine & The Projekt Game

A custom 2D Java game engine (`Octane`) featuring a demo game implementation (`theprojekt`).

## Description
This project is a simple 2D top-down stealth game with with a ranged vision. It is built upon "Octane Engine," a custom-made Java rendering and game loop system. It is unfinished but it was a fun project from my object-oriented programming class. 

## Controls
The game supports keyboard input:
* **Movement**: Arrow Keys (Up, Down, Left, Right)
* **Attack**: Spacebar (Throws knives)
* **Dash**: Shift
* **Quit**: Q

## Key Features
* **Entity System**: Support for NPCs, dynamic projectiles (knives), and static objects (chests, blockades).
* **Collision Detection**: Bounding box interaction between the player, enemies, and environment.
* **Audio System**: Background music and sound effects (combat, movement).
* **Resource Management**: Custom sprite and audio loading.

## Getting Started
1.  **Prerequisites**: Java Development Kit (JDK) 8 or higher.
2.  **Installation**: Clone the repository.
3.  **Run**: Execute the `main` method in `src/theprojekt/App.java`.

## Project Structure
* `src/doctrina`: The core engine handling the game loop, rendering, and inputs.
* `src/theprojekt`: The specific game logic, including Player, Maps, and NPC behaviors.
