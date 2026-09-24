Suppose:
Customer
---------
id
name
email

Order
---------
id
customerId
amount
status
createdAt

You need an API:
GET /customers/{customerId}/orders

But the frontend only needs:
[
{
"orderId": 101,
"amount": 500,
"status": "COMPLETED"
}
]

Task
Create:
OrderSummaryDto

and write a Spring Data JPA repository query that directly returns the DTO.
Don't fetch the complete Order entity if the API doesn't need it.
Then add:
status

as an optional filter.
So:
GET /customers/10/orders
GET /customers/10/orders?status=COMPLETED

Think about how you'd structure the repository/service layer.
Interview questions
1. Why use a DTO projection instead of returning the entity?
2. What problem does this solve?
3. What happens if you return the entity directly?
4. Where should filtering logic live?
5. Would you use JPQL or a derived query here?