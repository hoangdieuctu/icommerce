


## Query ##

1. Search products

Request
```json
{
	"branch": "Apple",
	"price": {
		"from": 30000000,
		"to": 40000000
	},
	"sort": {
		"sortBy": "branch",
		"orderBy": "asc"
	}
}
```

Response
```json
[
    {
        "id": 2,
        "name": "MacBook Air 2017",
        "branch": "Apple",
        "color": "White",
        "description": "Apply MacBook Air 2017 13 Inch",
        "buyPrice": 30000000,
        "qtyInStock": 5
    }
]
```