(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uv-category-my-suffix', {
            parent: 'entity',
            url: '/uv-category-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-categoriesmySuffix.html',
                    controller: 'UvCategoryMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('uv-category-my-suffix-detail', {
            parent: 'uv-category-my-suffix',
            url: '/uv-category-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-category-my-suffix-detail.html',
                    controller: 'UvCategoryMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UvCategory', function($stateParams, UvCategory) {
                    return UvCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uv-category-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uv-category-my-suffix-detail.edit', {
            parent: 'uv-category-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-category-my-suffix-dialog.html',
                    controller: 'UvCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvCategory', function(UvCategory) {
                            return UvCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-category-my-suffix.new', {
            parent: 'uv-category-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-category-my-suffix-dialog.html',
                    controller: 'UvCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categoryName: null,
                                categoryCode: null,
                                isEnabled: null,
                                createdOn: null,
                                lastUpdatedOn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uv-category-my-suffix', null, { reload: 'uv-category-my-suffix' });
                }, function() {
                    $state.go('uv-category-my-suffix');
                });
            }]
        })
        .state('uv-category-my-suffix.edit', {
            parent: 'uv-category-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-category-my-suffix-dialog.html',
                    controller: 'UvCategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvCategory', function(UvCategory) {
                            return UvCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-category-my-suffix', null, { reload: 'uv-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-category-my-suffix.delete', {
            parent: 'uv-category-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-category-my-suffix/uv-category-my-suffix-delete-dialog.html',
                    controller: 'UvCategoryMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UvCategory', function(UvCategory) {
                            return UvCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-category-my-suffix', null, { reload: 'uv-category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
