## User Story :: search book by title” on an e-commerce site
As a shopper, I want to search for books by title 
so that I can quickly find the book I’m looking for and add it to my cart.

### Acceptance Criteria (high level)
* Search box is visible on the Books page and site header.
* Hitting Enter or clicking the search icon triggers search.
* Results include book title, author, price, rating, availability, and thumbnail.
* Matching is case-insensitive and supports partial titles.
* Leading/trailing spaces are ignored.
* If no exact matches, show “Did you mean…?” suggestions when available.
* If no results at all, show an informative empty state with next steps.
* Results are sorted by relevance by default; user can sort by price/rating.
* Pagination or infinite scroll loads more results.
* Special characters and accents are handled safely (no errors).
* Performance: first page returns within 2s for typical queries.

### Data for test
* Initial ot setup data for testing !!
* Input
* Expected results


### Gherkin (Feature & Scenarios)
```
Feature: Search books by title
  In order to quickly find books to purchase
  As a shopper
  I want to search the catalog by book title and see relevant results

  Background:
    Given the catalog contains these books:
      | title                               | author                 | price | stock |
      | The Pragmatic Programmer            | Andrew Hunt            | 35.00 |  50   |
      | Pragmatic Thinking and Learning     | Andy Hunt              | 30.00 |  25   |
      | Clean Code                          | Robert C. Martin       | 40.00 |  10   |
      | Código Limpio                       | Robert C. Martin       | 38.00 |  12   |
      | Designing Data-Intensive Applications| Martin Kleppmann      | 55.00 |  15   |
    And I am on the "Books" page

  @happy_path
  Scenario: Exact title match by pressing Enter
    When I type "Clean Code" into the search box
    And I press Enter
    Then I should see 1 result
    And the first result title should be "Clean Code"

  @partial_match
  Scenario: Partial title returns multiple results sorted by relevance
    When I type "Pragmatic" into the search box
    And I click the search icon
    Then I should see at least 2 results
    And the results should include "The Pragmatic Programmer"
    And the results should include "Pragmatic Thinking and Learning"
    And results should be sorted by relevance

  @case_insensitive
  Scenario Outline: Case-insensitive and extra spaces are ignored
    When I type <query> into the search box
    And I press Enter
    Then the first result title should be "Clean Code"

    Examples:
      | query          |
      | "clean code"   |
      | "  Clean Code" |
      | "CLEAN CODE  " |

  @no_results_with_suggestion
  Scenario: No exact results but suggestion available
    Given the system can suggest corrections for misspelled queries
    When I type "Clen Code" into the search box
    And I press Enter
    Then I should see 0 results
    And I should see a suggestion "Did you mean 'Clean Code'?"
    And I can click the suggestion to run the corrected search
    When I click the suggestion
    Then I should see 1 result with title "Clean Code"

  @no_results
  Scenario: No results and no suggestion
    When I type "Nonexistent Book Title 123" into the search box
    And I press Enter
    Then I should see 0 results
    And I should see a helpful empty state message
    And I should see links to browse categories or clear filters

  @accents_and_special_chars
  Scenario: Accented characters and punctuation are handled
    When I type "Codigo Limpio" into the search box
    And I press Enter
    Then I should see results including "Código Limpio"

  @pagination
  Scenario: Pagination loads additional results
    Given there are more than 20 books matching "data"
    When I search for "data"
    Then I should see the first 20 results
    And I should see a "Next" control or infinite scroll
    When I request the next page
    Then I should see additional results without duplicates

  @sorting
  Scenario: User can change sort order
    When I search for "Pragmatic"
    And I change sort to "Price: Low to High"
    Then results should be ordered by ascending price

  @availability
  Scenario: Out-of-stock items are clearly labeled
    Given "The Pragmatic Programmer" is in stock
    And "Pragmatic Thinking and Learning" is out of stock
    When I search for "Pragmatic"
    Then each result shows availability
    And out-of-stock items display "Out of stock" and disable Add to Cart

  @performance
  Scenario: Search performance meets SLA
    When I search for "clean"
    Then the first page of results should load within 2 seconds

```