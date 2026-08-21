🔥 Main Implementation — Inventory Reservation

Build a small Spring Boot API:

POST /inventory/{productId}/reserve

Assume:

Product
---------
id
name
availableQuantity

Initial quantity:

availableQuantity = 10

The requirement:

Multiple users can simultaneously try to reserve inventory.

Task 1 — Build the basic implementation

Create:

Product
ProductRepository
InventoryService
InventoryController

Implement:

reserve(productId, quantity)

Rules:

Product must exist.
Quantity must be > 0.
Quantity cannot exceed available inventory.
If successful, decrease availableQuantity.
Return appropriate response.

Don't worry about concurrency yet.

Stop here.

Run it through Postman/curl/tests and make sure the normal case works.

Task 2 — Find the race condition

Now imagine:

availableQuantity = 1

Two requests arrive almost simultaneously:

User A → reserve(1)
User B → reserve(1)

Your current implementation may effectively do:

A reads quantity = 1
B reads quantity = 1


A → quantity = 0
B → quantity = 0


A succeeds
B succeeds

That's the problem we're interested in.

Your task:

Don't immediately look for the solution.

First answer:

Why can this happen?
What exactly is the race condition?
At what point must we prevent the two operations from interfering?
What would happen if you simply added Java synchronized?
Would synchronized solve the problem if you had 5 instances of the Spring Boot service?

Write your answers to me.

Task 3 — Optimistic locking

Now implement:

@Version

on the entity.

Your objective:

Request A ──┐
├── same product
Request B ──┘

should result in one request detecting the conflict.

Don't just tell me how it works.

Actually implement it.

Then intentionally create the conflict.

Task 4 — Don't solve everything today

This is important given what we just discussed.

Do NOT spend the whole night trying to complete all of this.

For Day 1, your main target is:

Basic reservation
↓
Understand race condition
↓
Implement optimistic locking
↓
Reproduce conflict

These can be parked for later:

⏸️ Concurrent automated test
⏸️ Retry mechanism
⏸️ Failure handling
⏸️ Pessimistic locking
⏸️ Atomic SQL solution

Those are future revisit points, not failures.


To complete:
⏸️ Retry mechanism
⏸️ Failure handling
⏸️ Pessimistic locking