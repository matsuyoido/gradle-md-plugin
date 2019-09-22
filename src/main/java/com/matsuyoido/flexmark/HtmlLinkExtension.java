package com.matsuyoido.flexmark;

import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

import java.util.Set;

import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * HtmlLinkExtension
 */
public class HtmlLinkExtension implements HtmlRendererExtension {

    @Override
    public void rendererOptions(MutableDataHolder options) {
    }

    @Override
    public void extend(Builder rendererBuilder, String rendererType) {
        rendererBuilder.linkResolverFactory(new ResolverFactory());
    }

    private class ResolverFactory implements LinkResolverFactory {
        @Override
        public LinkResolver apply(LinkResolverContext context) {
            return new HtmlLinkResolver();
        }

        @Override
        public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

    }
}

class HtmlLinkResolver implements LinkResolver {

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        if (node instanceof Link || node instanceof Reference) {
            String url = link.getUrl();
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return link;
            }
            if (url.endsWith(".md")) {
                return link.withStatus(LinkStatus.VALID)
                           .withUrl(url.substring(0, url.lastIndexOf(".md")) + ".html");
            }
        }
        return link;
    }

}