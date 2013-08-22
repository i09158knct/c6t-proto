json.array!(@routes) do |route|
  json.partial! route
  json.url route_url(route, format: :json)
end
