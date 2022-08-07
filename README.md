# Information on how to run, use the SCS and the attendant station softwares.
> Goal for iteration-3 of building a SelfCheckoutSystem is to work on previous usecases and implement new usecases which completes the entire SelfCheckoutSystem with GUI and JUnit testing.

<li><a href="https://github.com/adam-25/SENG-300-Iteration--2"><b>Iteration-2</b></li>

## Run Self Checkout System

<li>run "Demo.java" in \SCS - Software\src\seng300 as a java application.</li><br/>

```
LOGIN INFORMATION:
USERNAME: 87654321
PASSWORD: 12345678
```

## Attendant Station controls

<ul>
<li>Select station and press startup to start up that station.</li>
<li>select station and press shut down to shut down that station.</li>
<li>Select station and press block to block that station (blocking the station blocks paying or scanning items).</li>
<li>select station and press unblock to unblock that station.</li>
</ul>

## Attendant Station Hardware functionalities

<ul>
<li>Refill Coins: select a station and select refill coins (will block station for an attendant to go into the station and physically refill coins, once coins have been refilled, station will unblock).</li>
<li>Empty Coins: select a station and select Empty coins (will block station to allow attendant to go into the station and physically Empty coins)</li>
<li>Refill Banknotes: select a station and select refill banknotes (will block station for an attendant to go into the station and physically refill banknotes, once banknotes have been refilled, station will unblock).</li>
<li>Empty Banknotes: select a station and select Empty banknotes (will block station to allow attendant to go into the station and physically Empty banknotes).</li>
<li>Add Reciept Paper: select a station and select Add Reciept Paper: (will block station for an attendant to go into the Station printer and add reciept paper, once paper has been added, station will unblock).</li>
<li>Add Reciept Ink: select a station and select Add Reciept Ink: (will block station for an attendant to go into the Station printer and add reciept ink, once ink has been added, station will unblock).</li>
</ul>

## Customer Assistance
<ul>
<li>Lookup Product: allow attendant to look up product using a PLU Code (Price Lookup Code).</li>
<li>Remove Product: attendant can remove product from a selected station's cart.</li>
</ul>

## Self checkout station functionalities

<ul>
<li>Use own bag: asks the attendant station for permission for the customer to use their own bag to bag their items</li>
<li>scan product: Scan products by pressing scan item followed by place item, this will scan and place a random item in your bag</li>
<li>Enter PLU Code: Use Price Look Up codes from 00001 up to 00009 to add different items to cart.</li>
<li>Search product: Search products by name (for example Black Beans or Tomato Roma).</li>
<li>Remove Item: Gives you the option to remove items from the cart.</li>
<li>Do Not Bag: Simulates customer not putting scanned item in bagging area which should block the customer from scanning more items.</li>
<li>View Bagging Area: allows customer to view items placed in bagging area.</li>
<li>Pay: allows you to pay for items.</li>
</ul>

# Demo Video

[![Project Demo Video](https://img.youtube.com/vi/elc_yuuI-7Q/maxresdefault.jpg)](https://www.youtube.com/embed/elc_yuuI-7Q)
