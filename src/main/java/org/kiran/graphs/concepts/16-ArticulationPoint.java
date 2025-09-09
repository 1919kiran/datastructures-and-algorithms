/**
 * Algorithm:
 * We run a DFS to keep two numbers for any node - discoveryTime and lowestDiscoveryTime
 *  - discovery time (tin[u]): Time/steps taken in the dfs to reach the node
 *  - lowestDiscoveryTime: Minimum discovery time of all it's adjacent nodes except parent
 * a node u is an articulation point if:
 *  - it’s the root of dfs and has two or more independent children in the dfs tree. (if you remove root, those children subtrees get disconnected)
 *  - it’s a non-root and for some child v, low[v] >= tin[u]. that means v and its subtree cannot connect back to an ancestor of u, so without u, 
 *  that subtree gets cut off.
 * 
 */