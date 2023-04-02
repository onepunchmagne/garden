Feature: Beers catalog

  Scenario: Should add beer to catalog
    When I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    Then I should have beers catalog
      | Name              | Unit price |
      | Cloak of feathers | 8.53       |

  Scenario: Should remove beer from catalog
    Given I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    And I add beer to catalog
      | Name       | Ante meridium     |
      | Unit price | 5.46              |
    When I remove the created beer from the catalog
    Then I should have beers catalog
      | Name              | Unit price |
      | Cloak of feathers | 8.53       |
