import turtle
import time

# Create a new turtle screen and set its background color
screen = turtle.Screen()
screen.bgcolor("white")

# Create a new turtle object
clock = turtle.Turtle()
clock.hideturtle()

# Initialize hour, minute, and second variables
hour = 0
minute = 0
second = 0

# Initialize game time variables
game_hour = 0
game_minute = 0

while True:
    # Update the clock
    clock.clear()
    clock.penup()
    clock.goto(0, 50)
    clock.pendown()
    clock.write(f"{hour:02d}:{minute:02d}:{second:02d}", align="center", font=("Arial", 24, "bold"))

    # Update the game time
    clock.penup()
    clock.goto(0, -50)
    clock.pendown()
    clock.write(f"Game Time: {game_hour:02d}:{game_minute:02d}", align="center", font=("Arial", 24, "bold"))

    # Increment the clock and game time
    second += 1
    if second >= 60:
        minute += 1
        second = 0
    if minute >= 60:
        hour += 1
        minute = 0
    if hour >= 24:
        hour = 0

    # Increment the game time every 10 seconds
    if second % 10 == 0:
        game_minute += 5
        if game_minute >= 60:
            game_hour += 1
            game_minute = 0
        if game_hour >= 24:
            game_hour = 0

    # Wait for 1 second
    time.sleep(1)