# Description
This is a project for the Softuni Spring Advanced Module. The application is intended to act as a database that contains information about ingredients used in pet foods, food brands and products. It should act as a guide when choosing a food for your pet. It is not a store. Currently has only foods for cats and dogs.

# Functionalities

Unregistered users should be able to:
* Register
* View recommended products for any type of available in the application pet

Any user should be able to:
* View all ingredients
* Analyze ingredients
* View all foods
* View foods by pet brand
* View a product
* View product reviews

Logged users should be able to:
* Login
* View and edit their own user profile
* View products
* Add a product to their favorites
* Write a product review
* Like other user reviews
* View recommended products for the pet type assigned during registration/profile edit
* View Favorite products
* Logout

Moderators should be able to:
* Edit products

Admins should be able to:
* View admin area
* Add ingredient
* Add brand
* Add product
* Manage user roles

# Future/Upcoming upgrades
* Moderators and admins should be able to delete products
* Admins can delete brands
* Ingredient analyzer could provide information in a piechart 
* Implement product/review rating system
* Compare products (focus on ingredients)
* Categorize and filter ingredients / products
* Add possibility to add pictures to reviews
* Add more pet types

# Bugs and other fixes:
* users/login, users/register, users/my-profile, admin/manage-roles POST requests leads to redirect (status 302) after applying spring security chain filter. 
* user can't like two reviews on the same product
* Ingredient lookup should be key insensitive 
* Improve ingredient regex as ingredients can include various special characters
* Add binding models
