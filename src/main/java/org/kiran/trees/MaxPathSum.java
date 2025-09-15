class MaxPathSum {
    /**
     * the best “gain” we can give to our parent if the path continues upward through us. 
     * That must be a single chain: node.val + max(0, leftGain, rightGain). 
     * We drop negative gains since they would only hurt. the best “through” sum if the path ends here and uses 
     * both sides: node.val + max(0, leftGain) + max(0, rightGain). This candidate can update a global maximum answer.
     */
    public int maxPathSum(TreeNode root) {
        int[] ans = new int[1];
        maxPathSum(root, ans);
        return ans[0];
    }

    private int maxPathSum(TreeNode root, int[] maxVal) {
        if(root == null) return 0;
        int left = Math.max(0, maxPathSum(root.left, maxVal));
        int right = Math.max(0, maxPathSum(root.right, maxVal));
        maxVal = Math.max(maxVal, left+right+root.val);
        return root.val + Math.max(left, right);
    }
    
}