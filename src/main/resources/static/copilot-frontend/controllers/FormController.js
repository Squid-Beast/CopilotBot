app.controller('FormController', function($scope, $http) {
    $scope.address = {};      
    $scope.suggestions = [];  
    $scope.language = 'en';

    // Fetch address suggestions
    $scope.getSuggestions = function() {
        if ($scope.address.street1 && $scope.address.street1.length > 8) {  
            $http.get('/copilot/autocomplete?query=' + encodeURIComponent($scope.address.street1))
                .then(function(response) {
                    $scope.suggestions = response.data.results.map(result => ({
                        street_line: result.components.road || '',
                        city: result.components.city || result.components.town || result.components.village || '',
                        state: result.components.state || '',
                        zipcode: result.components.postcode || ''
                    }));
                })
                .catch(function(error) {
                    console.error("Error fetching address suggestions:", error);
                });
        } else {
            $scope.suggestions = []; 
        }
    };

    // Select a suggested address
    $scope.selectSuggestion = function(suggestion) {
        $scope.address.street1 = suggestion.street_line;
        $scope.address.city = suggestion.city;
        $scope.address.state = suggestion.state;
        $scope.address.zip = suggestion.zipcode;
        $scope.suggestions = []; 
    };
});