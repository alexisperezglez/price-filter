Feature:
  Scenario Outline: Success Filter Prices
    When A valid filter request is received with param <brandId>, <productId> and <applicationDate>
    Then A response list with values is returned
    Examples:
      | brandId | productId | applicationDate     |
      | 1       | 35455     | 2020-06-14 10:00:00 |
      | 1       | 35455     | 2020-06-14 16:00:00 |
      | 1       | 35455     | 2020-06-14 21:00:00 |
      | 1       | 35455     | 2020-06-15 10:00:00 |
      | 1       | 35455     | 2020-06-16 21:00:00 |

  Scenario: Prices Not Found
    When An outdated filter request is received
      | brandId         | 1                   |
      | productId       | 35455               |
      | applicationDate | 2021-06-14 17:00:00 |
    Then A 404 status code response is returned
